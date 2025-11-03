package cjy.controller;

import cjy.bean.Courier;
import cjy.bean.Message;
import cjy.mvc.annotation.ResponseBody;
import cjy.service.CourierService;
import cjy.util.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class CourierController {

    @ResponseBody("/courier/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        
        List<Map<String, Integer>> console = CourierService.console();
        Message message = new Message();
        if(console.size() == 0) {
            message.setStatus(-1);
            message.setResult("暂无数据");
        } else {
            message.setStatus(0);
            message.setResult("获取成功");
        }
        message.setData(console);
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/courier/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            int offset = Integer.parseInt(request.getParameter("offset"));
            int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            List<Courier> list = CourierService.findAll(true, offset, pageNumber);

            Message message = new Message();
            if(list != null && !list.isEmpty()) {
                message.setStatus(0);
                message.setResult("查询成功");
                message.setData(list);
            } else {
                message.setStatus(-1);
                message.setResult("暂无数据");
            }
            return JSONUtil.toJSON(message);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("查询失败: " + e.getMessage());
            return JSONUtil.toJSON(message);
        }
    }

    @ResponseBody("/courier/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String idCard = request.getParameter("idCard");
            String password = request.getParameter("password");

            System.out.println("接收快递员录入请求 - 姓名: " + name + ", 手机: " + phone +
                    ", 身份证: " + idCard + ", 密码: " + password);

            // 参数验证
            if(name == null || name.trim().isEmpty()) {
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("姓名不能为空");
                return JSONUtil.toJSON(message);
            }

            Courier courier = new Courier();
            courier.setName(name);
            courier.setPhone(phone);
            courier.setIdCard(idCard);
            courier.setPassword(password);

            boolean result = CourierService.insert(courier);
            Message message = new Message();
            if(result) {
                message.setStatus(0);
                message.setResult("快递员添加成功");
                System.out.println("快递员添加成功，姓名: " + name);
            } else {
                message.setStatus(-1);
                message.setResult("快递员添加失败");
                System.out.println("快递员添加失败，姓名: " + name);
            }
            return JSONUtil.toJSON(message);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("添加失败: " + e.getMessage());
            return JSONUtil.toJSON(message);
        }
    }

    @ResponseBody("/courier/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response) {
        try {
            String idStr = request.getParameter("id");
            Integer id = Integer.parseInt(idStr);
            Courier courier = CourierService.findById(id);

            Message message = new Message();
            if(courier != null) {
                message.setStatus(0);
                message.setResult("查询成功");
                message.setData(courier);
            } else {
                message.setStatus(-1);
                message.setResult("快递员不存在");
            }
            return JSONUtil.toJSON(message);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("查询失败: " + e.getMessage());
            return JSONUtil.toJSON(message);
        }
    }

    @ResponseBody("/courier/findByPhone.do")
    public String findByPhone(HttpServletRequest request, HttpServletResponse response) {
        try {
            String phone = request.getParameter("phone");
            Courier courier = CourierService.findByPhone(phone);

            Message message = new Message();
            if(courier != null) {
                message.setStatus(0);
                message.setResult("查询成功");
                message.setData(courier);
            } else {
                message.setStatus(-1);
                message.setResult("快递员不存在");
            }
            return JSONUtil.toJSON(message);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("查询失败: " + e.getMessage());
            return JSONUtil.toJSON(message);
        }
    }

    @ResponseBody("/courier/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response) {
        try {
            String idStr = request.getParameter("id");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String idCard = request.getParameter("idCard");
            String password = request.getParameter("password");
            String statusStr = request.getParameter("status");

            Integer id = Integer.parseInt(idStr);
            Integer status = statusStr != null ? Integer.parseInt(statusStr) : 1;

            System.out.println("修改快递员信息 - ID: " + id + ", 姓名: " + name +
                    ", 手机: " + phone + ", 身份证: " + idCard + ", 状态: " + status);

            Courier courier = new Courier();
            courier.setName(name);
            courier.setPhone(phone);
            courier.setIdCard(idCard);
            courier.setPassword(password);
            courier.setStatus(status);

            boolean result = CourierService.update(id, courier);
            Message message = new Message();
            if(result) {
                message.setStatus(0);
                message.setResult("修改成功");
            } else {
                message.setStatus(-1);
                message.setResult("修改失败");
            }
            return JSONUtil.toJSON(message);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("修改失败: " + e.getMessage());
            return JSONUtil.toJSON(message);
        }
    }

    @ResponseBody("/courier/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("=== 后端删除方法开始执行 ===");

        try {
            String idStr = request.getParameter("id");
            System.out.println("收到删除请求，ID参数: " + idStr);
            System.out.println("请求URL: " + request.getRequestURL());
            System.out.println("查询字符串: " + request.getQueryString());

            // 打印所有参数
            java.util.Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                System.out.println("参数 " + paramName + ": " + request.getParameter(paramName));
            }

            if(idStr == null || idStr.trim().isEmpty()) {
                System.out.println("删除失败：ID参数为空");
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("删除失败：ID不能为空");
                return JSONUtil.toJSON(message);
            }

            Integer id = Integer.parseInt(idStr);
            System.out.println("开始删除快递员，ID: " + id);

            boolean result = CourierService.delete(id);
            System.out.println("Service删除结果: " + result);

            Message message = new Message();
            if(result) {
                message.setStatus(0);
                message.setResult("删除成功");
                System.out.println("删除成功完成");
            } else {
                message.setStatus(-1);
                message.setResult("删除失败");
                System.out.println("删除失败完成");
            }

            String json = JSONUtil.toJSON(message);
            System.out.println("返回JSON: " + json);
            return json;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除异常: " + e.getMessage());
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("删除失败：" + e.getMessage());
            return JSONUtil.toJSON(message);
        }
    }
}
