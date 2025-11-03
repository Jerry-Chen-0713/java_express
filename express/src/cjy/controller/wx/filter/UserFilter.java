package cjy.controller.wx.filter;

import cjy.bean.User;
import cjy.util.UserUtil;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;


@WebFilter({"/index.html", "/wxUserhome.html", "/lazyboard.html", "/expressList.html", "/expressAssist.html"})
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("UserFilter初始化完成");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String requestURI = request.getRequestURI();
        System.out.println("=== UserFilter拦截请求 ===");
        System.out.println("请求URL: " + requestURI);
        System.out.println("Session ID: " + session.getId());

        // 使用与登录相同的方法检查用户
        User user = UserUtil.getLoginUser(session);

        // 调试信息
        System.out.println("登录用户: " + user);
        if (user != null) {
            System.out.println("用户手机号: " + user.getUserPhone());
        }

        // 检查所有Session属性（调试用）
        try {
            Collections.list(session.getAttributeNames()).forEach(attrName -> {
                System.out.println("Session属性: " + attrName + " = " + session.getAttribute(attrName));
            });
        } catch (Exception e) {
            System.out.println("获取Session属性列表失败: " + e.getMessage());
        }

        if(user != null){
            // 用户已登录，放行请求
            System.out.println("用户已登录，允许访问: " + requestURI);
            chain.doFilter(servletRequest, servletResponse);
        } else {
            // 用户未登录，重定向到登录页
            System.out.println("用户未登录，重定向到登录页");
            response.sendRedirect("login.html");
        }

        System.out.println("=== UserFilter处理完成 ===\n");
    }

    @Override
    public void destroy() {
        System.out.println("UserFilter销毁");
    }
}
