package cjy.dao;

import cjy.bean.Courier;
import java.util.List;
import java.util.Map;

public interface BaseCourierDao {
    List<Map<String, Integer>> console();
    List<Courier> findAll(boolean limit, int offset, int pageNumber);
    Courier findById(Integer id);
    Courier findByPhone(String phone);
    Boolean insert(Courier courier);
    Boolean update(Integer id, Courier courier);
    Boolean delete(Integer id);
    Boolean updateLoginTime(Integer id);
}