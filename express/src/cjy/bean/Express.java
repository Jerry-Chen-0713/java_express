package cjy.bean;

import java.sql.Timestamp;
import java.util.Objects;

public class Express {

    private int id;
    private String number;
    private String username;
    private String userPhone;
    private String company;
    private String code;
    private Timestamp inTime;
    private Timestamp outTime;
    private int status;
    private String sysPhone;
    private Integer lockerId;  // 关联的快递柜ID
    private String sendDistrict; // 发货区县
    private String receiveDistrict; // 收货区县
    private String routePath;  // 运输路径
    private Double totalDistance; // 总距离

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Express express = (Express) o;
        return id == express.id && status == express.status && number.equals(express.number) && username.equals(express.username) && userPhone.equals(express.userPhone) && company.equals(express.company) && code.equals(express.code) && inTime.equals(express.inTime) && outTime.equals(express.outTime) && sysPhone.equals(express.sysPhone) && Objects.equals(lockerId, express.lockerId) && Objects.equals(sendDistrict, express.sendDistrict) && Objects.equals(receiveDistrict, express.receiveDistrict) && Objects.equals(routePath, express.routePath) && Objects.equals(totalDistance, express.totalDistance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone, lockerId, sendDistrict, receiveDistrict, routePath, totalDistance);
    }

    @Override
    public String toString() {
        return "Express{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", company='" + company + '\'' +
                ", code='" + code + '\'' +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", status=" + status +
                ", sysPhone='" + sysPhone + '\'' +
                ", lockerId=" + lockerId +
                ", sendDistrict='" + sendDistrict + '\'' +
                ", receiveDistrict='" + receiveDistrict + '\'' +
                ", routePath='" + routePath + '\'' +
                ", totalDistance=" + totalDistance +
                '}';
    }

    public Express(String number, String username, String userPhone, String company, String sysPhone, String code) {
        this.number = number;
        this.username = username;
        this.userPhone = userPhone;
        this.company = company;
        this.sysPhone = sysPhone;
        this.code = code;
    }

    public Express(String number, String username, String userPhone, String company,String sysPhone) {
        this.number = number;
        this.username = username;
        this.userPhone = userPhone;
        this.company = company;
        this.sysPhone = sysPhone;
    }

    public Express() {
    }

    public Express(int id, String number, String username, String userPhone, String company, String code, Timestamp inTime, Timestamp outTime, int status, String sysPhone) {
        this.id = id;
        this.number = number;
        this.username = username;
        this.userPhone = userPhone;
        this.company = company;
        this.code = code;
        this.inTime = inTime;
        this.outTime = outTime;
        this.status = status;
        this.sysPhone = sysPhone;
    }

    public Express(String number, String username, String userPhone, String company, String sysPhone, Integer lockerId, String sendDistrict, String receiveDistrict) {
        this.number = number;
        this.username = username;
        this.userPhone = userPhone;
        this.company = company;
        this.sysPhone = sysPhone;
        this.lockerId = lockerId;
        this.sendDistrict = sendDistrict;
        this.receiveDistrict = receiveDistrict;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getInTime() {
        return inTime;
    }

    public void setInTime(Timestamp inTime) {
        this.inTime = inTime;
    }

    public Timestamp getOutTime() {
        return outTime;
    }

    public void setOutTime(Timestamp outTime) {
        this.outTime = outTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSysPhone() {
        return sysPhone;
    }

    public void setSysPhone(String sysPhone) {
        this.sysPhone = sysPhone;
    }

    public Integer getLockerId() {
        return lockerId;
    }

    public void setLockerId(Integer lockerId) {
        this.lockerId = lockerId;
    }


    public String getSendDistrict() {
        return sendDistrict;
    }

    public void setSendDistrict(String sendDistrict) {
        this.sendDistrict = sendDistrict;
    }

    public String getReceiveDistrict() {
        return receiveDistrict;
    }

    public void setReceiveDistrict(String receiveDistrict) {
        this.receiveDistrict = receiveDistrict;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

}
