package cjy.dao.impl;

import cjy.bean.Courier;
import cjy.dao.BaseCourierDao;
import cjy.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourierDaoImple implements BaseCourierDao {

    @Override
    public List<Map<String, Integer>> console() {
        List<Map<String, Integer>> data = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DruidUtil.getConnection();

            // 统计快递员总数和今日新增
            String sql = "SELECT " +
                    "COUNT(*) AS total, " +
                    "COUNT(CASE WHEN DATE(register_time) = CURDATE() THEN 1 END) AS todayNew " +
                    "FROM courier";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Map<String, Integer> map = new HashMap<>();
                map.put("total", rs.getInt("total"));
                map.put("todayNew", rs.getInt("todayNew"));
                data.add(map);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, pstmt, rs);
        }

        return data;
    }

    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        List<Courier> data = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DruidUtil.getConnection();
            String sql = "SELECT * FROM courier ORDER BY id DESC";

            if (limit) {
                sql += " LIMIT ?,?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, offset);
                pstmt.setInt(2, pageNumber);
            } else {
                pstmt = conn.prepareStatement(sql);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Courier courier = new Courier();
                courier.setId(rs.getInt("id"));
                courier.setName(rs.getString("name"));
                courier.setPhone(rs.getString("phone"));
                courier.setIdCard(rs.getString("id_card"));
                courier.setPassword(rs.getString("password"));
                courier.setDeliveryCount(rs.getInt("delivery_count"));
                courier.setRegisterTime(rs.getTimestamp("register_time"));
                courier.setLastLoginTime(rs.getTimestamp("last_login_time"));
                courier.setStatus(rs.getInt("status"));
                data.add(courier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, pstmt, rs);
        }

        return data;
    }

    @Override
    public Courier findById(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Courier courier = null;

        try {
            conn = DruidUtil.getConnection();
            String sql = "SELECT * FROM courier WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                courier = new Courier();
                courier.setId(rs.getInt("id"));
                courier.setName(rs.getString("name"));
                courier.setPhone(rs.getString("phone"));
                courier.setIdCard(rs.getString("id_card"));
                courier.setPassword(rs.getString("password"));
                courier.setDeliveryCount(rs.getInt("delivery_count"));
                courier.setRegisterTime(rs.getTimestamp("register_time"));
                courier.setLastLoginTime(rs.getTimestamp("last_login_time"));
                courier.setStatus(rs.getInt("status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, pstmt, rs);
        }

        return courier;
    }

    @Override
    public Courier findByPhone(String phone) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Courier courier = null;

        try {
            conn = DruidUtil.getConnection();
            String sql = "SELECT * FROM courier WHERE phone = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                courier = new Courier();
                courier.setId(rs.getInt("id"));
                courier.setName(rs.getString("name"));
                courier.setPhone(rs.getString("phone"));
                courier.setIdCard(rs.getString("id_card"));
                courier.setPassword(rs.getString("password"));
                courier.setDeliveryCount(rs.getInt("delivery_count"));
                courier.setRegisterTime(rs.getTimestamp("register_time"));
                courier.setLastLoginTime(rs.getTimestamp("last_login_time"));
                courier.setStatus(rs.getInt("status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, pstmt, rs);
        }

        return courier;
    }

    @Override
    public Boolean insert(Courier courier) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtil.getConnection();
            String sql = "INSERT INTO courier (name, phone, id_card, password) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, courier.getName());
            pstmt.setString(2, courier.getPhone());
            pstmt.setString(3, courier.getIdCard());
            pstmt.setString(4, courier.getPassword());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DruidUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public Boolean update(Integer id, Courier courier) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtil.getConnection();
            StringBuilder sql = new StringBuilder("UPDATE courier SET name = ?, phone = ?, id_card = ?");
            List<Object> params = new ArrayList<>();
            params.add(courier.getName());
            params.add(courier.getPhone());
            params.add(courier.getIdCard());

            // 如果密码不为空，则更新密码
            if (courier.getPassword() != null && !courier.getPassword().trim().isEmpty()) {
                sql.append(", password = ?");
                params.add(courier.getPassword());
            }

            // 更新状态
            if (courier.getStatus() != null) {
                sql.append(", status = ?");
                params.add(courier.getStatus());
            }

            sql.append(" WHERE id = ?");
            params.add(id);

            pstmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DruidUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public Boolean delete(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtil.getConnection();
            // 物理删除，直接从数据库中删除记录
            String sql = "DELETE FROM courier WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int result = pstmt.executeUpdate();
            System.out.println("删除快递员，ID: " + id + "，影响行数: " + result);

            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("删除快递员失败，ID: " + id + "，错误: " + e.getMessage());
            return false;
        } finally {
            DruidUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public Boolean updateLoginTime(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtil.getConnection();
            String sql = "UPDATE courier SET last_login_time = NOW() WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DruidUtil.close(conn, pstmt, null);
        }
    }
}