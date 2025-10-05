package cjy.dao;

import cjy.bean.User;
import java.util.List;
import java.util.Map;

public interface BaseUserDao {
    List<Map<String, Integer>> console();
    List<User> findAll(boolean limit, int offset, int pageNumber);
    User findById(Integer id);
    User findByUserName(String userName);
    User findByUserPhone(String userPhone);
    Boolean insert(User user);
    Boolean update(Integer id, User user);
    Boolean delete(Integer id);
    Boolean updateLoginTime(Integer id);
}
