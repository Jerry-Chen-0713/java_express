package cjy.util;

import cjy.bean.User;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class UserUtil {

    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUserName");
    }

    public static String getUserPhone(HttpSession session){
        //TODO:还没有编写柜子端，未存储任何的录入人信息
        return "18888888888";
    }

    // 原有的验证码方法
    public static String getLoginSMS(HttpSession session,String userPhone){
        return (String) session.getAttribute(userPhone);
    }

    public static void setLoginSMS(HttpSession session,String userPhone,String code){
        session.setAttribute(userPhone,code);
    }

    // 设置微信用户
    public static void setWxUser(HttpSession session, User user){
        session.setAttribute("wxUser",user);
    }

    // 获取微信用户
    public static User getWxUser(HttpSession session){
        return (User) session.getAttribute("wxUser");
    }

    // 新增：设置登录用户
    public static void setLoginUser(HttpSession session, User user) {
        session.setAttribute("loginUser", user);
    }

    // 新增：获取登录用户
    public static User getLoginUser(HttpSession session) {
        return (User) session.getAttribute("loginUser");
    }

    // 新增：检查用户是否登录
    public static boolean isLogin(HttpSession session) {
        return getLoginUser(session) != null;
    }

    // 新增：用户登出
    public static void logout(HttpSession session) {
        session.removeAttribute("loginUser");
    }

    // 新增：移除验证码
    public static void removeLoginSMS(HttpSession session, String phone) {
        session.removeAttribute(phone);
    }
}