package cjy.dao;

import cjy.bean.Express;
import cjy.exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

public interface BaseExpressDao {

    List<Map<String, Integer>> console();

    List<Express> findAll(boolean limit, int offset, int pageNumber);

    Express findByNumber(String number);

    Express findByCode(String code);

    List<Express> findByUserPhone(String userPhone);

    List<Express> findByUserPhoneAndStatus(String userPhone,int status);

    List<Express> findBySysPhone(String sysPhone);

    boolean insert(Express express) throws DuplicateCodeException;

    boolean update(int id, Express newExpress);

    boolean updateStatus(String number);

    boolean delete(int id);

    /**
     * 根据取件码获取快递柜ID
     *
     * @param code 取件码
     * @return 快递柜ID，如果不存在返回null
     */
    Integer getLockerIdByCode(String code);

    /**
     * 根据取件码删除快递数据
     *
     * @param code 取件码
     * @return 删除的结果，true表示成功，false表示失败
     */
    boolean deleteByCode(String code);

    /**
     * 更新快递的柜子信息
     *
     * @param id 快递ID
     * @param lockerId 快递柜ID
     * @return 更新结果，true表示成功，false表示失败
     */
    boolean updateLockerId(int id, Integer lockerId);

    /**
     * 获取快递区域统计数据
     *
     * @return 区域统计数据列表
     */
    List<Map<String, Object>> getRegionStats();

}
