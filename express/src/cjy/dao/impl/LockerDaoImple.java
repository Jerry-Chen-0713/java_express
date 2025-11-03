package cjy.dao.impl;

import cjy.bean.Locker;
import cjy.dao.BaseLockerDao;
import cjy.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 快递柜数据访问实现类
 */
public class LockerDaoImple implements BaseLockerDao {

    @Override
    public List<Locker> console() {
        ArrayList<Locker> data = new ArrayList<>();
        String sql = "SELECT COUNT(id) AS total, COUNT(TO_DAYS(in_time)=TO_DAYS(NOW()) OR NULL) AS today FROM locker";
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(sql);
            result = state.executeQuery();
            if (result.next()) {
                int total = result.getInt("total");
                int today = result.getInt("today");
                Locker locker = new Locker();
                // 这里可以根据需要设置统计信息
                data.add(locker);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, result);
        }
        return data;
    }

    @Override
    public List<Locker> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Locker> data = new ArrayList<>();
        String sql = "SELECT * FROM locker";
        if (limit) {
            sql += " LIMIT ?,?";
        }
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(sql);
            if (limit) {
                state.setInt(1, offset);
                state.setInt(2, pageNumber);
            }
            result = state.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String expressNumber = result.getString("express_number");
                String district = result.getString("district");
                String size = result.getString("size");
                int status = result.getInt("status");
                
                // 使用默认构造函数和setter方法
                Locker locker = new Locker();
                locker.setId(id);
                locker.setExpressNumber(expressNumber);
                locker.setDistrict(district);
                locker.setSize(size);
                locker.setStatus(status);
                data.add(locker);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, result);
        }
        return data;
    }

    @Override
    public Locker findById(int id) {
        String sql = "SELECT * FROM locker WHERE id=?";
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(sql);
            state.setInt(1, id);
            result = state.executeQuery();
            if (result.next()) {
                String expressNumber = result.getString("express_number");
                String district = result.getString("district");
                String size = result.getString("size");
                int status = result.getInt("status");
                
                // 使用默认构造函数和setter方法
                Locker locker = new Locker();
                locker.setId(id);
                locker.setExpressNumber(expressNumber);
                locker.setDistrict(district);
                locker.setSize(size);
                locker.setStatus(status);
                return locker;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, result);
        }
        return null;
    }

    @Override
    public Locker findByExpressNumber(String expressNumber) {
        String sql = "SELECT * FROM locker WHERE express_number=?";
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            state = conn.prepareStatement(sql);
            state.setString(1, expressNumber);
            result = state.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                String district = result.getString("district");
                String size = result.getString("size");
                int status = result.getInt("status");
                
                // 使用默认构造函数和setter方法
                Locker locker = new Locker();
                locker.setId(id);
                locker.setExpressNumber(expressNumber);
                locker.setDistrict(district);
                locker.setSize(size);
                locker.setStatus(status);
                return locker;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, result);
        }
        return null;
    }

    @Override
    public List<Locker> findAvailableLockers(String district, String size) {
        ArrayList<Locker> data = new ArrayList<>();
        String sql = "SELECT * FROM locker WHERE district=? AND size=? AND status=0";
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            System.out.println("=== DAO层: 查询可用快递柜 ===");
            System.out.println("=== DAO层: SQL: " + sql);
            System.out.println("=== DAO层: 参数 - district=" + district + ", size=" + size);
            
            state = conn.prepareStatement(sql);
            state.setString(1, district);
            state.setString(2, size);
            result = state.executeQuery();
            
            int count = 0;
            while (result.next()) {
                count++;
                int id = result.getInt("id");
                String expressNumber = result.getString("express_number");
                
                // 使用默认构造函数和setter方法
                Locker locker = new Locker();
                locker.setId(id);
                locker.setExpressNumber(expressNumber);
                locker.setDistrict(district);
                locker.setSize(size);
                locker.setStatus(0);
                data.add(locker);
                System.out.println("=== DAO层: 找到快递柜 " + count + ": ID=" + id + ", expressNumber=" + expressNumber);
            }
            System.out.println("=== DAO层: 查询完成，共找到 " + count + " 个可用快递柜 ===");
            
        } catch (SQLException throwables) {
            System.err.println("=== DAO层: 查询出错 ===");
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, result);
        }
        return data;
    }

    @Override
    public boolean insert(Locker locker) {
        String sql = "INSERT INTO locker(express_number, district, size, status) VALUES(?,?,?,?)";
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(sql);
            state.setString(1, locker.getExpressNumber());
            state.setString(2, locker.getDistrict());
            state.setString(3, locker.getSize());
            state.setInt(4, locker.getStatus());
            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    @Override
    public boolean update(int id, Locker newLocker) {
        String sql = "UPDATE locker SET express_number=?, district=?, size=?, status=? WHERE id=?";
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(sql);
            state.setString(1, newLocker.getExpressNumber());
            state.setString(2, newLocker.getDistrict());
            state.setString(3, newLocker.getSize());
            state.setInt(4, newLocker.getStatus());
            state.setInt(5, id);
            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    @Override
    public boolean assignLocker(int lockerId, String expressNumber) {
        String sql = "UPDATE locker SET express_number=?, status=1 WHERE id=? AND status=0";
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(sql);
            state.setString(1, expressNumber);
            state.setInt(2, lockerId);
            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    @Override
    public boolean releaseLocker(int lockerId) {
        String sql = "UPDATE locker SET express_number=NULL, status=0 WHERE id=?";
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(sql);
            state.setInt(1, lockerId);
            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    @Override
    public List<Locker> findAllByDistrictAndSize(String district, String size) {
        ArrayList<Locker> data = new ArrayList<>();
        String sql = "SELECT * FROM locker WHERE district=? AND size=?";
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        try {
            System.out.println("=== DAO层: 查询所有快递柜 ===");
            System.out.println("=== DAO层: SQL: " + sql);
            System.out.println("=== DAO层: 参数 - district=" + district + ", size=" + size);
            
            state = conn.prepareStatement(sql);
            state.setString(1, district);
            state.setString(2, size);
            result = state.executeQuery();
            
            int count = 0;
            while (result.next()) {
                count++;
                int id = result.getInt("id");
                String expressNumber = result.getString("express_number");
                int status = result.getInt("status");
                
                // 使用默认构造函数和setter方法
                Locker locker = new Locker();
                locker.setId(id);
                locker.setExpressNumber(expressNumber);
                locker.setDistrict(district);
                locker.setSize(size);
                locker.setStatus(status);
                data.add(locker);
                System.out.println("=== DAO层: 找到快递柜 " + count + ": ID=" + id + ", expressNumber=" + expressNumber + ", status=" + status);
            }
            System.out.println("=== DAO层: 查询完成，共找到 " + count + " 个快递柜 ===");
            
        } catch (SQLException throwables) {
            System.err.println("=== DAO层: 查询出错 ===");
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, result);
        }
        return data;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM locker WHERE id=?";
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(sql);
            state.setInt(1, id);
            return state.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }
        return false;
    }
}
