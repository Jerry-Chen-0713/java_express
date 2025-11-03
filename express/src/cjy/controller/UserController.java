package cjy.controller;

import cjy.bean.Message;
import cjy.bean.ResultData;
import cjy.bean.User;
import cjy.mvc.annotation.ResponseBody;
import cjy.service.UserService;
import cjy.util.JSONUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class UserController {

    @ResponseBody("/user/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        
        List<Map<String, Integer>> console = UserService.console();
        Message message = new Message();
        if(console.size() == 0) {
            message.setStatus(-1);
        } else {
            message.setStatus(0);
        }
        message.setData(console);
        String json = JSONUtil.toJSON(message);
        return json;
    }

    @ResponseBody("/user/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response){
        // 设置响应编码，确保中文正确显示
        response.setContentType("application/json;charset=UTF-8");
        
        // 获取查询数据的起始索引值
        int offset = 0;
        int pageNumber = 10;
        try {
            String offsetStr = request.getParameter("offset");
            String pageNumberStr = request.getParameter("pageNumber");
            if (offsetStr != null && !offsetStr.trim().isEmpty()) {
                offset = Integer.parseInt(offsetStr);
            }
            if (pageNumberStr != null && !pageNumberStr.trim().isEmpty()) {
                pageNumber = Integer.parseInt(pageNumberStr);
            }
        } catch (NumberFormatException e) {
            // 使用默认值
        }
        
        List<User> list = UserService.findAll(true, offset, pageNumber);
        
        List<Map<String, Integer>> console = UserService.console();
        Integer total = console.get(0).get("data1_size");
        ResultData<User> data = new ResultData<>();
        data.setRows(list);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    @ResponseBody("/user/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response){
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        
        String idStr = request.getParameter("id");
        User user = UserService.findById(Integer.parseInt(idStr));
        Message message = new Message();
        if(user != null){
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(user);
        }else{
            message.setStatus(-1);
            message.setResult("用户不存在");
        }
        String json = JSONUtil.toJSON(message);
        return json;
    }

    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        // 设置请求和响应编码
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("application/json;charset=UTF-8");
        
        String userName = request.getParameter("userName");
        String userPhone = request.getParameter("userPhone");
        String password = request.getParameter("password");
        
        User user = new User();
        user.setUserName(userName);
        user.setUserPhone(userPhone);
        user.setPassword(password != null ? password : "123456");
        user.setUser(true);
        
        Message message = new Message();
        boolean insert = UserService.insert(user);
        if(insert){
            message.setStatus(0);
            message.setResult("用户录入成功！");
        }else{
            message.setStatus(-1);
            message.setResult("用户录入失败！");
        }
        
        String json = JSONUtil.toJSON(message);
        return json;
    }

    @ResponseBody("/user/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response){
        // 设置请求和响应编码
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            String idStr = request.getParameter("id");
            String userName = request.getParameter("userName");
            String userPhone = request.getParameter("userPhone");
            String password = request.getParameter("password");

            if(idStr == null || idStr.trim().isEmpty()) {
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("修改失败：ID不能为空");
                return JSONUtil.toJSON(message);
            }

            Integer id = Integer.parseInt(idStr);

            User newUser = new User();
            newUser.setUserName(userName);
            newUser.setUserPhone(userPhone);
            if (password != null && !password.trim().isEmpty()) {
                newUser.setPassword(password);
            }

            boolean updateResult = UserService.update(id, newUser);

            Message message = new Message();
            if(updateResult){
                message.setStatus(0);
                message.setResult("修改成功！");
            } else {
                message.setStatus(-1);
                message.setResult("修改失败！");
            }
            return JSONUtil.toJSON(message);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("修改失败：参数格式错误");
            return JSONUtil.toJSON(message);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("修改失败：" + e.getMessage());
            return JSONUtil.toJSON(message);
        }
    }

    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        try {
            String idStr = request.getParameter("id");

            if(idStr == null || idStr.trim().isEmpty()) {
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("删除失败：ID不能为空");
                return JSONUtil.toJSON(message);
            }

            Integer id = Integer.parseInt(idStr);

            boolean deleteResult = UserService.delete(id);

            Message message = new Message();
            if(deleteResult){
                message.setStatus(0);
                message.setResult("删除成功！");
            } else {
                message.setStatus(-1);
                message.setResult("删除失败！用户不存在或已被删除");
            }
            return JSONUtil.toJSON(message);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("删除失败：ID格式错误");
            return JSONUtil.toJSON(message);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("删除失败：" + e.getMessage());
            return JSONUtil.toJSON(message);
        }
    }
}
