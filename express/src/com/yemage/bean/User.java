package com.yemage.bean;

import java.sql.Timestamp;

public class User {
    private int id;
    private String userName;
    private String userPhone;
    private String password;
    private Timestamp createTime;
    private Timestamp loginTime;
    private boolean user;

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public User() {
    }

    public User(int id, String userName, String userPhone, String password, Timestamp createTime, Timestamp loginTime) {
        this.id = id;
        this.userName = userName;
        this.userPhone = userPhone;
        this.password = password;
        this.createTime = createTime;
        this.loginTime = loginTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }
}
