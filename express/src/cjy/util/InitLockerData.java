package cjy.util;

import cjy.service.LockerService;

/**
 * 初始化快递柜数据的工具类
 */
public class InitLockerData {
    
    public static void main(String[] args) {
        System.out.println("开始初始化快递柜数据...");
        try {
            LockerService.initLockers();
            System.out.println("快递柜数据初始化成功！");
        } catch (Exception e) {
            System.err.println("快递柜数据初始化失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
