package cjy.dao.impl;

import cjy.bean.User;
import cjy.dao.BaseUserDao;
import cjy.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImple implements BaseUserDao {

    @Override
    public List<Map<String, Integer>> console() {
        List<Map<String, Integer>> data = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DruidUtil.getConnection();

            // 统计用户总数和今日新增
            String sql = "SELECT " +
                    "COUNT(*) AS total, " +
                    "COUNT(CASE WHEN DATE(create_time) = CURDATE() THEN 1 END) AS todayNew " +
                    "FROM user WHERE is_user = 1";

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
    public List<User> findAll(boolean limit, int offset, int pageNumber) {
        List<User> data = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DruidUtil.getConnection();
            String sql = "SELECT * FROM user ORDER BY id DESC";

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
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserPhone(rs.getString("user_phone"));
                user.setPassword(rs.getString("password"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                user.setLoginTime(rs.getTimestamp("login_time"));
                user.setUser(rs.getBoolean("is_user"));
                data.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, pstmt, rs);
        }

        return data;
    }

    @Override
    public User findById(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DruidUtil.getConnection();
            String sql = "SELECT * FROM user WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserPhone(rs.getString("user_phone"));
                user.setPassword(rs.getString("password"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                user.setLoginTime(rs.getTimestamp("login_time"));
                user.setUser(rs.getBoolean("is_user"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, pstmt, rs);
        }

        return user;
    }

    @Override
    public User findByUserName(String userName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DruidUtil.getConnection();
            String sql = "SELECT * FROM user WHERE user_name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserPhone(rs.getString("user_phone"));
                user.setPassword(rs.getString("password"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                user.setLoginTime(rs.getTimestamp("login_time"));
                user.setUser(rs.getBoolean("is_user"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, pstmt, rs);
        }

        return user;
    }

    @Override
    public User findByUserPhone(String userPhone) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DruidUtil.getConnection();
            String sql = "SELECT * FROM user WHERE user_phone = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userPhone);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserPhone(rs.getString("user_phone"));
                user.setPassword(rs.getString("password"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                user.setLoginTime(rs.getTimestamp("login_time"));
                user.setUser(rs.getBoolean("is_user"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, pstmt, rs);
        }

        return user;
    }

    @Override
    public Boolean insert(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtil.getConnection();
            String sql = "INSERT INTO user (user_name, user_phone, password, is_user) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserPhone());
            pstmt.setString(3, user.getPassword());
            pstmt.setBoolean(4, user.isUser());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DruidUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public Boolean update(Integer id, User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtil.getConnection();
            StringBuilder sql = new StringBuilder("UPDATE user SET user_name = ?, user_phone = ?");
            List<Object> params = new ArrayList<>();
            params.add(user.getUserName());
            params.add(user.getUserPhone());

            // 如果密码不为空，则更新密码
            if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
                sql.append(", password = ?");
                params.add(user.getPassword());
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
            String sql = "DELETE FROM user WHERE id = ? AND is_user = 1"; // 只能删除普通用户
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

    @Override
    public Boolean updateLoginTime(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DruidUtil.getConnection();
            String sql = "UPDATE user SET login_time = NOW() WHERE id = ?";
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
