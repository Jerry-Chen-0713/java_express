package cjy.dao.impl;

import cjy.bean.Express;
import cjy.dao.BaseExpressDao;
import cjy.exception.DuplicateCodeException;
import cjy.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressDaoImple implements BaseExpressDao {

    //用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
    public static final String SQL_CONSOLE = "SELECT COUNT(ID) data1_size,COUNT(TO_DAYS(in_time)=TO_DAYS(NOW()) OR NULL) data1_day,COUNT(STATUS=0 OR NUll) data2_size,COUNT(TO_DAYS(in_time)=TO_DAYS(NOW()) AND STATUS=0 OR NULL) data2_day FROM EXPRESS";
    //用于分页查询数据库中的快递信息
    public static final String SQL_FIND_LIMIT = "SELECT *FROM EXPRESS LIMIT ?,?";
    //用于查询数据库中的所有快递信息
    public static final String SQL_FIND_ALL = "SELECT * FROM EXPRESS";
    //根据快递单号，查询快递信息
    public static final String SQL_FIND_BY_NUMBER = "SELECT * FROM EXPRESS WHERE NUMBER=?";
    //根据取件码，查询快递信息
    public static final String SQL_FIND_BY_CODE = "SELECT * FROM EXPRESS WHERE CODE=?";
    //根据用户手机号，查询快递信息
    public static final String SQL_FIND_BY_USERPHONE = "SELECT * FROM EXPRESS WHERE USERPHONE=?";
    //根据录入人手机号，查询快递信息
    public static final String SQL_FIND_BY_SYSPHONE = "SELECT * FROM EXPRESS WHERE SYSPHONE=?";
    //录入快递
    public static final String SQL_INSERT = "INSERT INTO EXPRESS (NUMBER,USERNAME,USERPHONE,COMPANY,CODE,in_time,STATUS,SYSPHONE,LOCKER_ID,SEND_DISTRICT,RECEIVE_DISTRICT) VALUES(?,?,?,?,?,NOW(),0,?,?,?,?)";
    //修改快递
    public static final String SQL_UPDATE = "UPDATE EXPRESS SET NUMBER=?,USERNAME=?,COMPANY=?,STATUS=? WHERE ID=?";
    //更新快递柜ID
    public static final String SQL_UPDATE_LOCKER_ID = "UPDATE EXPRESS SET LOCKER_ID=? WHERE ID=?";
    //快递的状态码改变（取件）
    public static final String SQL_UPDATE_STATUS = "UPDATE EXPRESS SET STATUS=1,out_time=NOW(),CODE=NULL WHERE CODE=?";
    //快递的删除
    public static final String SQL_DELETE = "DELETE FROM EXPRESS WHERE ID=?";
    private static final String SQL_FIND_BY_USERPHONE_AND_STATUS = "SELECT * FROM EXPRESS WHERE USERPHONE=? AND STATUS=?";

    @Override
    public List<Map<String, Integer>> console() {
        List<Map<String, Integer>> data = new ArrayList<>();
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_CONSOLE);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int data1_size = resultSet.getInt("data1_size");
                int data1_day = resultSet.getInt("data1_day");
                int data2_size = resultSet.getInt("data2_size");
                int data2_day = resultSet.getInt("data2_day");
                Map data1 = new HashMap();
                data1.put("data1_size",data1_size);
                data1.put("data1_day",data1_day);
                Map data2 = new HashMap();
                data2.put("data2_size",data2_size);
                data2.put("data2_day",data2_day);
                data.add(data1);
                data.add(data2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return data;
    }

    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        List<Express> data = new ArrayList<>();
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            if(limit){
                preparedStatement = connection.prepareStatement(SQL_FIND_LIMIT);
                preparedStatement.setInt(1,offset);
                preparedStatement.setInt(2,pageNumber);
            }else{
                preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
            }
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("in_time");
                Timestamp outTime = resultSet.getTimestamp("out_time");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express express = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(express);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return data;
    }

    @Override
    public Express findByNumber(String number) {
        System.out.println("=== DAO层: 开始根据快递单号查询: '" + number + "' ===");
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_BY_NUMBER);
            preparedStatement.setString(1,number);
            System.out.println("=== DAO层: 执行SQL: " + SQL_FIND_BY_NUMBER + " 参数: " + number + " ===");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("in_time");
                Timestamp outTime = resultSet.getTimestamp("out_time");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Integer lockerId = resultSet.getInt("locker_id");
                if (resultSet.wasNull()) {
                    lockerId = null;
                }
                String sendDistrict = resultSet.getString("send_district");
                String receiveDistrict = resultSet.getString("receive_district");
                Express express = new Express();
                express.setId(id);
                express.setNumber(number);
                express.setUsername(username);
                express.setUserPhone(userPhone);
                express.setCompany(company);
                express.setCode(code);
                express.setInTime(inTime);
                express.setOutTime(outTime);
                express.setStatus(status);
                express.setSysPhone(sysPhone);
                express.setLockerId(lockerId);
                express.setSendDistrict(sendDistrict);
                express.setReceiveDistrict(receiveDistrict);
                System.out.println("=== DAO层: 找到快递: ID=" + id + ", 状态=" + status + ", 取件码=" + code + ", 收货区域=" + receiveDistrict + " ===");
                return express;
            } else {
                System.out.println("=== DAO层: 未找到快递单号: " + number + " ===");
            }
        } catch (SQLException throwables) {
            System.err.println("=== DAO层: 查询出错 ===");
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    @Override
    public Express findByCode(String code) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_BY_CODE);
            preparedStatement.setString(1,code);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                Timestamp inTime = resultSet.getTimestamp("in_time");
                Timestamp outTime = resultSet.getTimestamp("out_time");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express express = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                return express;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    @Override
    public List<Express> findByUserPhone(String userPhone) {
        List<Express> data = new ArrayList<>();
        System.out.println("=== DAO层: 开始查询用户手机号: '" + userPhone + "' ===");
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_BY_USERPHONE);
            preparedStatement.setString(1, userPhone);
            System.out.println("=== DAO层: 执行SQL: " + SQL_FIND_BY_USERPHONE + " 参数: " + userPhone + " ===");
            resultSet = preparedStatement.executeQuery();
            int count = 0;
            while(resultSet.next()){
                count++;
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("in_time");
                Timestamp outTime = resultSet.getTimestamp("out_time");
                int status = resultSet.getInt("status");
                String sysPhone = resultSet.getString("sysPhone");
                Express express = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(express);
                System.out.println("=== DAO层: 找到快递 " + count + ": ID=" + id + ", 单号=" + number + ", 状态=" + status + " ===");
            }
            System.out.println("=== DAO层: 查询完成，共找到 " + count + " 条记录 ===");
        } catch (SQLException throwables) {
            System.err.println("=== DAO层: 查询出错 ===");
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return data;
    }

    @Override
    public List<Express> findByUserPhoneAndStatus(String userPhone, int status) {
        List<Express> data = new ArrayList<>();
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_BY_USERPHONE_AND_STATUS);
            preparedStatement.setString(1,userPhone);
            preparedStatement.setInt(2,status);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("in_time");
                Timestamp outTime = resultSet.getTimestamp("out_time");
                String sysPhone = resultSet.getString("sysPhone");
                Express express = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(express);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return data;
    }

    @Override
    public List<Express> findBySysPhone(String sysPhone) {
        List<Express> data = new ArrayList<>();
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_BY_SYSPHONE);
            preparedStatement.setString(1,sysPhone);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String number = resultSet.getString("number");
                String username = resultSet.getString("username");
                String userPhone = resultSet.getString("userPhone");
                String company = resultSet.getString("company");
                String code = resultSet.getString("code");
                Timestamp inTime = resultSet.getTimestamp("in_time");
                Timestamp outTime = resultSet.getTimestamp("out_time");
                int status = resultSet.getInt("status");
                Express express = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(express);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(connection,preparedStatement,resultSet);
        }
        return data;
    }

    @Override
    public boolean insert(Express express) throws DuplicateCodeException{
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1,express.getNumber());
            preparedStatement.setString(2,express.getUsername());
            preparedStatement.setString(3,express.getUserPhone());
            preparedStatement.setString(4,express.getCompany());
            preparedStatement.setString(5,express.getCode());
            preparedStatement.setString(6,express.getSysPhone());
            if (express.getLockerId() != null) {
                preparedStatement.setInt(7, express.getLockerId());
            } else {
                preparedStatement.setNull(7, java.sql.Types.INTEGER);
            }
            preparedStatement.setString(8, express.getSendDistrict());
            preparedStatement.setString(9, express.getReceiveDistrict());
            System.out.println("=== DAO层: 执行SQL: " + SQL_INSERT);
            System.out.println("=== DAO层: 参数: number=" + express.getNumber() + 
                ", username=" + express.getUsername() + 
                ", userPhone=" + express.getUserPhone() + 
                ", company=" + express.getCompany() + 
                ", code=" + express.getCode() + 
                ", sysPhone=" + express.getSysPhone() + 
                ", lockerId=" + express.getLockerId() + 
                ", sendDistrict=" + express.getSendDistrict() + 
                ", receiveDistrict=" + express.getReceiveDistrict());
            int result = preparedStatement.executeUpdate();
            System.out.println("=== DAO层: 执行结果: " + result + " ===");
            return result > 0;
        } catch (SQLException throwables) {
            System.err.println("=== DAO层: SQL执行出错 ===");
            System.err.println("错误信息: " + throwables.getMessage());
            System.err.println("SQL状态: " + throwables.getSQLState());
            System.err.println("错误代码: " + throwables.getErrorCode());
            System.err.println("SQL语句: " + SQL_INSERT);
            System.err.println("参数详情: number=" + express.getNumber() + 
                ", username=" + express.getUsername() + 
                ", userPhone=" + express.getUserPhone() + 
                ", company=" + express.getCompany() + 
                ", code=" + express.getCode() + 
                ", sysPhone=" + express.getSysPhone() + 
                ", lockerId=" + express.getLockerId() + 
                ", sendDistrict=" + express.getSendDistrict() + 
                ", receiveDistrict=" + express.getReceiveDistrict());
            throwables.printStackTrace();
            if(throwables.getMessage().endsWith("for key 'express.code'")){
                DuplicateCodeException e = new DuplicateCodeException(throwables.getMessage());
                throw e;
            }
        }finally {
            DruidUtil.close(connection,preparedStatement,null);
        }
        return false;
    }

    @Override
    public boolean update(int id, Express newExpress) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1,newExpress.getNumber());
            preparedStatement.setString(2,newExpress.getUsername());
            preparedStatement.setString(3,newExpress.getCompany());
            preparedStatement.setInt(4,newExpress.getStatus());
            preparedStatement.setInt(5,id);
            return preparedStatement.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(connection,preparedStatement,null);
        }
        return false;
    }

    @Override
    public boolean updateStatus(String code) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS);
            preparedStatement.setString(1,code);
            return preparedStatement.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(connection,preparedStatement,null);
        }
        return false;
    }

    @Override
    public Integer getLockerIdByCode(String code) {
        String sql = "SELECT locker_id FROM express WHERE code=?";
        Connection connection = DruidUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, code);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Integer lockerId = rs.getInt("locker_id");
                if (rs.wasNull()) {
                    return null;
                }
                return lockerId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(connection, pstmt, rs);
        }
        return null;
    }

    @Override
    public boolean deleteByCode(String code) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setString(1, code);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(connection, preparedStatement, null);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = DruidUtil.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(connection, preparedStatement, null);
        }
        return false;
    }

    @Override
    public boolean updateLockerId(int id, Integer lockerId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DruidUtil.getConnection();
            String sql = "UPDATE express SET locker_id = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, lockerId);
            pstmt.setInt(2, id);
            
            int rows = pstmt.executeUpdate();
            System.out.println("=== DAO层: 更新快递柜ID结果 - 影响行数: " + rows + " ===");
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("=== DAO层: 更新快递柜ID失败 ===");
            return false;
        } finally {
            DruidUtil.close(conn, pstmt, null);
        }
    }

    @Override
    public List<Map<String, Object>> getRegionStats() {
        List<Map<String, Object>> data = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DruidUtil.getConnection();
            
            // 统计每个区域的快递数量（包括发送和接收区域）
            String sql = "SELECT " +
                    "CASE " +
                    "   WHEN send_district IS NOT NULL AND send_district != '' THEN send_district " +
                    "   WHEN receive_district IS NOT NULL AND receive_district != '' THEN receive_district " +
                    "   ELSE '未知区域' " +
                    "END AS district, " +
                    "COUNT(*) AS count " +
                    "FROM express " +
                    "WHERE status = 0 " + // 只统计未取件的快递
                    "GROUP BY district " +
                    "ORDER BY count DESC";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", rs.getString("district"));
                map.put("value", rs.getInt("count"));
                data.add(map);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(conn, pstmt, rs);
        }

        return data;
    }
}
