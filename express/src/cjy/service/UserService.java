package cjy.service;

import cjy.bean.User;
import cjy.dao.BaseUserDao;
import cjy.dao.impl.UserDaoImple;
import java.util.List;
import java.util.Map;

public class UserService {
    private static BaseUserDao userDao = new UserDaoImple();

    public static List<Map<String, Integer>> console() {
        return userDao.console();
    }

    public static List<User> findAll(boolean limit, int offset, int pageNumber) {
        return userDao.findAll(limit, offset, pageNumber);
    }

    public static User findById(Integer id) {
        return userDao.findById(id);
    }

    public static User findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    public static User findByUserPhone(String userPhone) {
        return userDao.findByUserPhone(userPhone);
    }

    public static Boolean insert(User user) {
        return userDao.insert(user);
    }

    public static Boolean update(Integer id, User user) {
        return userDao.update(id, user);
    }

    public static Boolean delete(Integer id) {
        return userDao.delete(id);
    }

    public static Boolean updateLoginTime(Integer id) {
        return userDao.updateLoginTime(id);
    }
}