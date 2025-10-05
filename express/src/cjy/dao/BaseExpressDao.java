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






}
