package cjy.service;

import cjy.bean.Express;
import cjy.dao.BaseExpressDao;
import cjy.dao.impl.ExpressDaoImple;
import cjy.exception.DuplicateCodeException;
import cjy.util.RandomUtil;
import cjy.util.SMSUtil;

import java.util.List;
import java.util.Map;

public class ExpressService{

    private static BaseExpressDao dao = new ExpressDaoImple();

    /**
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     *
     * @return [{size：总数，day：新增}，{size:总数，day:新增}]
     */

    public static List<Map<String, Integer>> console() {

        return dao.console();
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的表级，true表示分页，false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */

    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 快递单号
     * @return 查询的快递信息，单号不存在时，返回null
     */

    public static Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    /**
     * 根据取件码慢查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */

    public static Express findByCode(String code) {
        return dao.findByCode(code);
    }

    /**
     * 根据用户手机号码查询快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */

    public static List<Express> findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * 根据用户手机号码和状态查询快递信息
     *
     * @param userPhone 手机号码
     * @param status 状态
     * @return 查询的快递信息列表
     */

    public static List<Express> findByUserPhoneAndStatus(String userPhone, int status) {
        return dao.findByUserPhoneAndStatus(userPhone, status);
    }

    /**
     * 根据录入人的手机号，查询录入的所有记录
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息列表
     */

    public static List<Express> findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }

    /**
     * 快递的录入
     *
     * @param express 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */

    public static boolean insert(Express express) throws DuplicateCodeException {
        return dao.insert(express);
    }

    /**
     * 快递的录入
     *
     * @param express 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */

    public static boolean insert2(Express express) {
        try {
            boolean flag = dao.insert(express);
            if(flag){
                //发送短信
                SMSUtil.send(express.getUserPhone(),express.getCode());
            }
            return flag;
        } catch (DuplicateCodeException e) {
            try {
                return insert(express);
            } catch (DuplicateCodeException e2) {
                e2.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 快递的修改
     *
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象（number，company，username，userPhone）
     * @return 修改的结果，true表示成功，false表示失败
     */

    public static boolean update(int id, Express newExpress) {
        if(newExpress.getUserPhone() != null){
            dao.delete(id);
            try {
                return insert(newExpress);
            } catch (DuplicateCodeException e) {
                e.printStackTrace();
                return false;
            }

        }else{
            boolean update = dao.update(id, newExpress);
            Express e = dao.findByNumber(newExpress.getNumber());
            if(newExpress.getStatus() == 1){
                updateStatus(e.getCode());
            }
            return update;
        }

    }

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递单号
     * @return 修改的结果，true表示成功，false表示失败
     */

    public static boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    /**
     * 根据取件码获取快递柜ID
     *
     * @param code 取件码
     * @return 快递柜ID，如果不存在返回null
     */
    public static Integer getLockerIdByCode(String code) {
        return dao.getLockerIdByCode(code);
    }

    /**
     * 根据取件码删除快递数据
     *
     * @param code 取件码
     * @return 删除的结果，true表示成功，false表示失败
     */
    public static boolean deleteByCode(String code) {
        return dao.deleteByCode(code);
    }

    /**
     * 取出快递并释放快递柜
     *
     * @param code 取件码
     * @return 操作结果，true表示成功，false表示失败
     */
    public static boolean pickExpressAndReleaseLocker(String code) {
        System.out.println("=== ExpressService: 开始取出快递并释放快递柜，取件码: " + code + " ===");
        
        try {
            // 1. 获取快递柜ID
            Integer lockerId = getLockerIdByCode(code);
            System.out.println("=== ExpressService: 获取到的快递柜ID: " + lockerId + " ===");
            
            if (lockerId == null) {
                System.err.println("=== ExpressService: 未找到对应的快递柜ID ===");
                return false;
            }
            
            // 2. 更新快递状态为已取出（status=1），保留所有数据
            boolean updateResult = updateStatus(code);
            System.out.println("=== ExpressService: 更新快递状态结果: " + updateResult + " ===");
            
            if (!updateResult) {
                System.err.println("=== ExpressService: 更新快递状态失败 ===");
                return false;
            }
            
            // 3. 释放快递柜
            boolean releaseResult = LockerService.releaseLocker(lockerId);
            System.out.println("=== ExpressService: 释放快递柜结果: " + releaseResult + " ===");
            
            if (!releaseResult) {
                System.err.println("=== ExpressService: 释放快递柜失败 ===");
                return false;
            }
            
            System.out.println("=== ExpressService: 取出快递并释放快递柜成功 ===");
            return true;
            
        } catch (Exception e) {
            System.err.println("=== ExpressService: 取出快递并释放快递柜过程中出现异常 ===");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据id，删除单个快递信息
     *
     * @param id 要删除的快递的id
     * @return 删除的结果，true表示成功，false表示失败
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }

    /**
     * 更新快递的柜子信息
     *
     * @param id 快递ID
     * @param lockerId 快递柜ID
     * @return 更新结果，true表示成功，false表示失败
     */
    public static boolean updateLockerId(int id, Integer lockerId) {
        System.out.println("=== ExpressService: 更新快递柜ID - id=" + id + ", lockerId=" + lockerId + " ===");
        // 直接调用DAO层的updateLockerId方法
        return dao.updateLockerId(id, lockerId);
    }

    /**
     * 获取快递区域统计数据
     *
     * @return 区域统计数据列表
     */
    public static List<Map<String, Object>> getRegionStats() {
        return dao.getRegionStats();
    }
}
