package cjy.service;

import cjy.bean.Courier;
import cjy.dao.BaseCourierDao;
import cjy.dao.impl.CourierDaoImple;
import java.util.List;
import java.util.Map;

public class CourierService {
    private static BaseCourierDao courierDao = new CourierDaoImple();

    public static List<Map<String, Integer>> console() {
        return courierDao.console();
    }

    public static List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return courierDao.findAll(limit, offset, pageNumber);
    }

    public static Courier findById(Integer id) {
        return courierDao.findById(id);
    }

    public static Courier findByPhone(String phone) {
        return courierDao.findByPhone(phone);
    }

    public static Boolean insert(Courier courier) {
        return courierDao.insert(courier);
    }

    public static Boolean update(Integer id, Courier courier) {
        return courierDao.update(id, courier);
    }

    public static Boolean delete(Integer id) {
        return courierDao.delete(id);
    }
}
