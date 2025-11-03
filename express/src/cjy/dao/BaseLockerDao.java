package cjy.dao;

import cjy.bean.Locker;

import java.util.List;

/**
 * 快递柜数据访问接口
 */
public interface BaseLockerDao {
    
    /**
     * 查询数据库中的快递柜统计信息
     * @return 统计信息列表
     */
    List<Locker> console();
    
    /**
     * 查询所有快递柜
     * @param limit 是否分页
     * @param offset 起始索引
     * @param pageNumber 每页数量
     * @return 快递柜列表
     */
    List<Locker> findAll(boolean limit, int offset, int pageNumber);
    
    /**
     * 根据ID查询快递柜
     * @param id 快递柜ID
     * @return 快递柜对象
     */
    Locker findById(int id);
    
    /**
     * 根据快递单号查询快递柜
     * @param expressNumber 快递单号
     * @return 快递柜对象
     */
    Locker findByExpressNumber(String expressNumber);
    
    /**
     * 根据区域和容量查询空闲的快递柜
     * @param district 区域
     * @param size 容量（大、中、小）
     * @return 空闲快递柜列表
     */
    List<Locker> findAvailableLockers(String district, String size);
    
    /**
     * 插入新的快递柜
     * @param locker 快递柜对象
     * @return 是否成功
     */
    boolean insert(Locker locker);
    
    /**
     * 更新快递柜信息
     * @param id 快递柜ID
     * @param newLocker 新的快递柜信息
     * @return 是否成功
     */
    boolean update(int id, Locker newLocker);
    
    /**
     * 分配快递柜给快递
     * @param lockerId 快递柜ID
     * @param expressNumber 快递单号
     * @return 是否成功
     */
    boolean assignLocker(int lockerId, String expressNumber);
    
    /**
     * 释放快递柜
     * @param lockerId 快递柜ID
     * @return 是否成功
     */
    boolean releaseLocker(int lockerId);
    
    /**
     * 删除快递柜
     * @param id 快递柜ID
     * @return 是否成功
     */
    boolean delete(int id);

    /**
     * 根据区域和容量查询所有快递柜（包括已使用和空闲的）
     * @param district 区域
     * @param size 容量（大、中、小）
     * @return 快递柜列表
     */
    List<Locker> findAllByDistrictAndSize(String district, String size);
}
