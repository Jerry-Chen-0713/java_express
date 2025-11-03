package cjy.service;

import cjy.bean.Locker;
import cjy.dao.BaseLockerDao;
import cjy.dao.impl.LockerDaoImple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快递柜服务类
 */
public class LockerService {

    private static BaseLockerDao lockerDao = new LockerDaoImple();

    /**
     * 根据区域查询空闲的快递柜
     * @param district 区域
     * @param size 快递柜容量（大、中、小）
     * @return 空闲的快递柜列表
     */
    public static List<Locker> findAvailableLockers(String district, String size) {
        return lockerDao.findAvailableLockers(district, size);
    }

    /**
     * 检查指定区域和容量的快递柜是否已满
     * @param district 区域
     * @param size 快递柜容量（大、中、小）
     * @return 是否已满
     */
    public static boolean isLockerFull(String district, String size) {
        List<Locker> availableLockers = findAvailableLockers(district, size);
        return availableLockers.isEmpty();
    }

    /**
     * 获取快递柜统计数据
     *
     * @param district 区域
     * @return 快递柜统计数据列表
     */
    public static List<Map<String, Object>> getLockerStats(String district) {
        System.out.println("=== LockerService: 获取快递柜统计数据 - district=" + district + " ===");
        
        List<Map<String, Object>> stats = new ArrayList<>();
        
        // 查询大号柜子
        List<Locker> largeLockers = lockerDao.findAllByDistrictAndSize(district, "大");
        Map<String, Object> largeStats = new HashMap<>();
        largeStats.put("size", "大");
        largeStats.put("total", largeLockers.size());
        largeStats.put("used", countUsedLockers(largeLockers));
        largeStats.put("available", largeLockers.size() - countUsedLockers(largeLockers));
        stats.add(largeStats);
        
        // 查询中号柜子
        List<Locker> mediumLockers = lockerDao.findAllByDistrictAndSize(district, "中");
        Map<String, Object> mediumStats = new HashMap<>();
        mediumStats.put("size", "中");
        mediumStats.put("total", mediumLockers.size());
        mediumStats.put("used", countUsedLockers(mediumLockers));
        mediumStats.put("available", mediumLockers.size() - countUsedLockers(mediumLockers));
        stats.add(mediumStats);
        
        // 查询小号柜子
        List<Locker> smallLockers = lockerDao.findAllByDistrictAndSize(district, "小");
        Map<String, Object> smallStats = new HashMap<>();
        smallStats.put("size", "小");
        smallStats.put("total", smallLockers.size());
        smallStats.put("used", countUsedLockers(smallLockers));
        smallStats.put("available", smallLockers.size() - countUsedLockers(smallLockers));
        stats.add(smallStats);
        
        System.out.println("=== LockerService: 快递柜统计数据 - " + stats + " ===");
        return stats;
    }
    
    /**
     * 统计已使用的快递柜数量
     *
     * @param lockers 快递柜列表
     * @return 已使用的快递柜数量
     */
    private static int countUsedLockers(List<Locker> lockers) {
        int count = 0;
        for (Locker locker : lockers) {
            if (locker.getStatus() == 1) {
                count++;
            }
        }
        return count;
    }

    /**
     * 分配快递柜
     * @param district 区域
     * @param size 快递柜容量（大、中、小）
     * @param expressNumber 快递单号
     * @return 分配的快递柜ID，如果没有可用快递柜返回null
     */
    public static Integer assignLocker(String district, String size, String expressNumber) {
        System.out.println("=== LockerService: 开始分配快递柜 ===");
        System.out.println("=== LockerService: 参数 - district=" + district + ", size=" + size + ", expressNumber=" + expressNumber);
        
        List<Locker> availableLockers = findAvailableLockers(district, size);
        System.out.println("=== LockerService: 找到可用快递柜数量: " + availableLockers.size());
        
        if (availableLockers.isEmpty()) {
            System.out.println("=== LockerService: 没有可用快递柜，返回null ===");
            return null;
        }
        
        // 分配第一个可用的快递柜
        Locker locker = availableLockers.get(0);
        System.out.println("=== LockerService: 分配快递柜ID: " + locker.getId());
        
        boolean success = lockerDao.assignLocker(locker.getId(), expressNumber);
        System.out.println("=== LockerService: 分配结果: " + success);
        
        return success ? locker.getId() : null;
    }

    /**
     * 释放快递柜
     * @param lockerId 快递柜ID
     * @return 是否成功释放
     */
    public static boolean releaseLocker(int lockerId) {
        return lockerDao.releaseLocker(lockerId);
    }

    /**
     * 获取快递柜信息
     * @param lockerId 快递柜ID
     * @return 快递柜对象
     */
    public static Locker getLockerById(int lockerId) {
        return lockerDao.findById(lockerId);
    }

    /**
     * 初始化快递柜数据
     * 每个区域设置大中小三种快递柜各5个
     */
    public static void initLockers() {
        System.out.println("=== LockerService: 开始初始化快递柜数据 ===");
        
        // 只创建上海的快递柜，包含所有16个区
        String[] shanghaiDistricts = {
            "黄浦区", "徐汇区", "长宁区", "静安区", "普陀区", "虹口区", "杨浦区", 
            "闵行区", "宝山区", "嘉定区", "浦东新区", "金山区", "松江区", 
            "青浦区", "奉贤区", "崇明区"
        };
        
        String[] sizes = {"大", "中", "小"};
        
        int totalCreated = 0;
        
        // 为上海的每个区域创建快递柜
        for (String district : shanghaiDistricts) {
            for (String size : sizes) {
                // 每种容量创建5个快递柜
                for (int j = 0; j < 5; j++) {
                    Locker locker = new Locker("上海", district, size, 0);
                    boolean success = lockerDao.insert(locker);
                    if (success) {
                        totalCreated++;
                    } else {
                        System.err.println("=== LockerService: 创建快递柜失败 - 上海 " + district + " " + size + " ===");
                    }
                }
            }
        }
        
        System.out.println("=== LockerService: 快递柜数据初始化完成，共创建 " + totalCreated + " 个快递柜 ===");
    }
}
