package cjy.bean;

import java.sql.Timestamp;

public class Courier {
    private Integer id;
    private String name;
    private String phone;
    private String idCard;
    private String password;
    private Integer deliveryCount;
    private Timestamp registerTime;
    private Timestamp lastLoginTime;
    private Integer status;

    // 构造方法
    public Courier() {}

    // getter和setter方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getIdCard() { return idCard; }
    public void setIdCard(String idCard) { this.idCard = idCard; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getDeliveryCount() { return deliveryCount; }
    public void setDeliveryCount(Integer deliveryCount) { this.deliveryCount = deliveryCount; }

    public Timestamp getRegisterTime() { return registerTime; }
    public void setRegisterTime(Timestamp registerTime) { this.registerTime = registerTime; }

    public Timestamp getLastLoginTime() { return lastLoginTime; }
    public void setLastLoginTime(Timestamp lastLoginTime) { this.lastLoginTime = lastLoginTime; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}