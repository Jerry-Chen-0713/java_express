package cjy.bean;

import java.util.Objects;

/**
 * 快递柜实体类
 */
public class Locker {
    private int id; // 快递柜id（自增）
    private String expressNumber; // 快递号（会随着快递的取出和寄入改变）
    private String city; // 快递柜所属城市
    private String district; // 快递柜属于的区
    private String size; // 快递柜容量（大、中、小）
    private int status; // 状态（0-空闲，1-占用）

    public Locker() {
    }

    public Locker(String city, String district, String size, int status) {
        this.city = city;
        this.district = district;
        this.size = size;
        this.status = status;
    }

    public Locker(int id, String expressNumber, String city, String district, String size, int status) {
        this.id = id;
        this.expressNumber = expressNumber;
        this.city = city;
        this.district = district;
        this.size = size;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locker locker = (Locker) o;
        return id == locker.id && status == locker.status && 
               Objects.equals(expressNumber, locker.expressNumber) && 
               Objects.equals(city, locker.city) && 
               Objects.equals(district, locker.district) && 
               Objects.equals(size, locker.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expressNumber, city, district, size, status);
    }

    @Override
    public String toString() {
        return "Locker{" +
                "id=" + id +
                ", expressNumber='" + expressNumber + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", size='" + size + '\'' +
                ", status=" + status +
                '}';
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
