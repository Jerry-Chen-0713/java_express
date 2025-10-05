package cjy.controller.wx;

import cjy.bean.User;
import cjy.util.UserUtil;
import cjy.bean.Message;
import cjy.mvc.annotation.ResponseBody;
import cjy.service.UserService;
import cjy.util.JSONUtil;
import cjy.util.RandomUtil;
import cjy.util.SMSUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public class UserController {

    @ResponseBody("/user/sendSMS.do")
    public String sendSMS(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json;charset=UTF-8");
        String userPhone = request.getParameter("userPhone");
        String code = RandomUtil.getCode() + "";
        boolean flag = SMSUtil.send(userPhone, code);
        //boolean flag = true;
        //String code = "123456";
        Message message = new Message();
        if(flag){
            //短信发送成功
            message.setStatus(0);
            message.setResult("验证码已发送，请查收！");
        }else{
            //短信发送失败
            message.setStatus(1);
            message.setResult("验证码下发失败，请检查手机号或稍后再试！");
        }
        UserUtil.setLoginSMS(request.getSession(),userPhone,code);
        String json = JSONUtil.toJSON(message);
        return json;
    }

    @ResponseBody("/user/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");

        String userPhone = request.getParameter("userPhone");
        String code = request.getParameter("code");

        Message message = new Message();

        // 参数验证
        if(userPhone == null || userPhone.trim().isEmpty()) {
            message.setStatus(-1);
            message.setResult("手机号不能为空");
            return JSONUtil.toJSON(message);
        }

        if(code == null || code.trim().isEmpty()) {
            message.setStatus(-1);
            message.setResult("验证码不能为空");
            return JSONUtil.toJSON(message);
        }

        return loginByCode(request, response, userPhone, code);
    }

    // 验证码登录
    private String loginByCode(HttpServletRequest request, HttpServletResponse response, String userPhone, String code) {
        Message message = new Message();

        try {
            System.out.println("=== 开始登录验证 ===");
            System.out.println("用户手机号: " + userPhone);
            System.out.println("用户输入验证码: " + code);

            // 从session中获取保存的验证码信息
            String savedCode = UserUtil.getLoginSMS(request.getSession(), userPhone);
            System.out.println("Session中保存的验证码: " + savedCode);

            if(savedCode == null) {
                System.out.println("验证码已过期或不存在");
                message.setStatus(-1);
                message.setResult("验证码已过期，请重新获取");
                return JSONUtil.toJSON(message);
            }

            if(!savedCode.equals(code)) {
                System.out.println("验证码不匹配，输入:" + code + ", 保存:" + savedCode);
                message.setStatus(-1);
                message.setResult("验证码错误");
                return JSONUtil.toJSON(message);
            }

            System.out.println("验证码验证成功");

            // 验证码正确，查找用户
            User user = UserService.findByUserPhone(userPhone);
            System.out.println("查找用户结果: " + user);

            if(user == null) {
                System.out.println("用户不存在，开始自动注册");
                // 用户不存在，自动注册
                user = new User();
                user.setUserPhone(userPhone);
                user.setUserName("用户_" + userPhone.substring(7));
                user.setPassword(RandomUtil.getCode() + "");
                user.setUser(true);

                boolean registerResult = UserService.insert(user);
                System.out.println("自动注册结果: " + registerResult);

                if(!registerResult) {
                    message.setStatus(-1);
                    message.setResult("自动注册失败，请重试");
                    return JSONUtil.toJSON(message);
                }

                // 重新获取用户信息
                user = UserService.findByUserPhone(userPhone);
                System.out.println("重新获取用户: " + user);
            }

            // 更新登录时间
            UserService.updateLoginTime(user.getId());

            // 设置用户登录状态
            UserUtil.setLoginUser(request.getSession(), user);
            System.out.println("用户登录状态设置完成");

            message.setStatus(0);
            message.setResult("登录成功");
            message.setData(user);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("登录异常: " + e.getMessage());
            message.setStatus(-1);
            message.setResult("登录失败：" + e.getMessage());
        }

        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/user/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response) {
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
        try {
            int offset = Integer.parseInt(request.getParameter("offset"));
            int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            List<User> list = UserService.findAll(true, offset, pageNumber);

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

    @ResponseBody("/user/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response){
        try {
            String idStr = request.getParameter("id");
            Integer id = Integer.parseInt(idStr);
            User user = UserService.findById(id);

            Message message = new Message();
            if(user != null) {
                message.setStatus(0);
                message.setResult("查询成功");
                message.setData(user);
            } else {
                message.setStatus(-1);
                message.setResult("用户不存在");
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

    @ResponseBody("/user/findByPhone.do")
    public String findByPhone(HttpServletRequest request, HttpServletResponse response){
        try {
            String userPhone = request.getParameter("userPhone");
            User user = UserService.findByUserPhone(userPhone);

            Message message = new Message();
            if(user != null) {
                message.setStatus(0);
                message.setResult("查询成功");
                message.setData(user);
            } else {
                message.setStatus(-1);
                message.setResult("用户不存在");
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

    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        try {
            String userName = request.getParameter("userName");
            String userPhone = request.getParameter("userPhone");
            String password = request.getParameter("password");

            // 参数验证
            if(userName == null || userName.trim().isEmpty()) {
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("用户名不能为空");
                return JSONUtil.toJSON(message);
            }

            if(userPhone == null || userPhone.trim().isEmpty()) {
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("手机号不能为空");
                return JSONUtil.toJSON(message);
            }

            User user = new User();
            user.setUserName(userName);
            user.setUserPhone(userPhone);
            user.setPassword(password != null ? password : "123456"); // 默认密码
            user.setUser(true); // 普通用户

            boolean result = UserService.insert(user);
            Message message = new Message();
            if(result) {
                message.setStatus(0);
                message.setResult("用户添加成功");
            } else {
                message.setStatus(-1);
                message.setResult("用户添加失败");
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

    @ResponseBody("/user/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response){
        try {
            String idStr = request.getParameter("id");
            String userName = request.getParameter("userName");
            String userPhone = request.getParameter("userPhone");
            String password = request.getParameter("password");

            Integer id = Integer.parseInt(idStr);

            User user = new User();
            user.setUserName(userName);
            user.setUserPhone(userPhone);
            user.setPassword(password);

            boolean result = UserService.update(id, user);
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

    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        try {
            String idStr = request.getParameter("id");
            Integer id = Integer.parseInt(idStr);

            boolean result = UserService.delete(id);
            Message message = new Message();
            if(result) {
                message.setStatus(0);
                message.setResult("删除成功");
            } else {
                message.setStatus(-1);
                message.setResult("删除失败");
            }
            return JSONUtil.toJSON(message);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("删除失败: " + e.getMessage());
            return JSONUtil.toJSON(message);
        }
    }

    @ResponseBody("/user/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        UserUtil.logout(request.getSession());
        Message message = new Message();
        message.setStatus(0);
        message.setResult("退出成功");
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/user/checkLogin.do")
    public String checkLogin(HttpServletRequest request, HttpServletResponse response){
        User user = UserUtil.getLoginUser(request.getSession());
        Message message = new Message();
        if(user != null) {
            message.setStatus(0);
            message.setResult("用户已登录");
            message.setData(user);
        } else {
            message.setStatus(-1);
            message.setResult("用户未登录");
        }
        return JSONUtil.toJSON(message);
    }
}