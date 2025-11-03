package cjy.controller.wx;

import cjy.bean.Express;
import cjy.bean.Message;
import cjy.bean.User;
import cjy.mvc.annotation.ResponseBody;
import cjy.service.ExpressService;
import cjy.util.DateFormatUtil;
import cjy.util.JSONUtil;
import cjy.util.UserUtil;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class ExpressController {

    @ResponseBody("/wx/findExpressByUserPhone.do")
    public String findExpressByUserPhone(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 使用 getLoginUser 而不是 getWxUser
            User loginUser = UserUtil.getLoginUser(request.getSession());

            // 检查用户是否为空
            if (loginUser == null) {
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("用户未登录");
                return JSONUtil.toJSON(message);  // 返回完整的message
            }

            // 检查用户手机号是否为空
            if (loginUser.getUserPhone() == null) {
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("用户信息不完整");
                return JSONUtil.toJSON(message);  // 返回完整的message
            }

            String userPhone = loginUser.getUserPhone();
            List<Express> list = ExpressService.findByUserPhone(userPhone);

            // 直接使用Express类，避免重复的BootstrapExpress类
            List<Express> formattedList = new ArrayList<>();
            for(Express e:list){
                // 创建新的Express对象，设置格式化后的字段
                Express formattedExpress = new Express();
                formattedExpress.setId(e.getId());
                formattedExpress.setNumber(e.getNumber());
                formattedExpress.setUsername(e.getUsername());
                formattedExpress.setUserPhone(e.getUserPhone());
                formattedExpress.setCompany(e.getCompany());
                formattedExpress.setCode(e.getCode() == null ? "已取件" : e.getCode());
                formattedExpress.setInTime(e.getInTime());
                formattedExpress.setOutTime(e.getOutTime());
                formattedExpress.setStatus(e.getStatus());
                formattedExpress.setSysPhone(e.getSysPhone());
                formattedExpress.setLockerId(e.getLockerId());
            formattedExpress.setSendDistrict(e.getSendDistrict());
            formattedExpress.setReceiveDistrict(e.getReceiveDistrict());
                formattedList.add(formattedExpress);
            }

            Message message = new Message();
            if(formattedList.size() == 0){
                message.setStatus(-1);
                message.setResult("暂无快递信息");
                message.setData(new ArrayList<>());  // 设置空数组而不是null
            } else {
                message.setStatus(0);
                message.setResult("查询成功");
                Stream<Express> expressStream1 = formattedList.stream().filter(express -> {
                    if (express.getStatus() == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }).sorted((o1, o2) -> {
                    long o1Time = o1.getInTime().getTime();
                    long o2Time = o2.getInTime().getTime();
                    return (int) (o1Time - o2Time);
                });
                Stream<Express> expressStream2 = formattedList.stream().filter(express -> {
                    if (express.getStatus() == 1) {
                        return true;
                    } else {
                        return false;
                    }
                }).sorted((o1, o2) -> {
                    long o1Time = o1.getInTime().getTime();
                    long o2Time = o2.getInTime().getTime();
                    return (int) (o1Time - o2Time);
                });
                Object[] array1 = expressStream1.toArray();
                Object[] array2 = expressStream2.toArray();
                Map data = new HashMap();
                data.put("status1",array1);
                data.put("status2",array2);
                message.setData(data);
            }

            // 修改这里：返回完整的message对象
            return JSONUtil.toJSON(message);

        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("系统错误: " + e.getMessage());
            return JSONUtil.toJSON(message);  // 返回完整的message
        }
    }

    @ResponseBody("/wx/userExpressList.do")
    public String expressList(HttpServletRequest request,HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        List<Express> list = ExpressService.findByUserPhoneAndStatus(userPhone, 0);
        // 直接使用Express类，避免重复的BootstrapExpress类
        List<Express> formattedList = new ArrayList<>();

        for(Express e:list){
            // 创建新的Express对象，设置格式化后的字段
            Express formattedExpress = new Express();
            formattedExpress.setId(e.getId());
            formattedExpress.setNumber(e.getNumber());
            formattedExpress.setUsername(e.getUsername());
            formattedExpress.setUserPhone(e.getUserPhone());
            formattedExpress.setCompany(e.getCompany());
            formattedExpress.setCode(e.getCode() == null ? "已取件" : e.getCode());
            formattedExpress.setInTime(e.getInTime());
            formattedExpress.setOutTime(e.getOutTime());
            formattedExpress.setStatus(e.getStatus());
            formattedExpress.setSysPhone(e.getSysPhone());
            formattedExpress.setLockerId(e.getLockerId());
            formattedExpress.setSendDistrict(e.getSendDistrict());
            formattedExpress.setReceiveDistrict(e.getReceiveDistrict());
            formattedList.add(formattedExpress);
        }
        Message message = new Message();
        if(formattedList.size() == 0){
            message.setStatus(-1);
            message.setResult("未查询到的快递");
        }else{
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(formattedList);
        }
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/wx/findExpressByNumberOrPhone.do")
    public String findExpressByNumberOrPhone(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyword");
        Message message = new Message();

        if (keyword == null || keyword.trim().isEmpty()) {
            message.setStatus(-1);
            message.setResult("请输入快递单号或手机号");
            return JSONUtil.toJSON(message);
        }

        List<Express> list = new ArrayList<>();

        // 判断是否为手机号
        if (keyword.matches("^1[3-9]\\d{9}$")) {
            list = ExpressService.findByUserPhone(keyword);
        } else {
            Express e = ExpressService.findByNumber(keyword);
            if (e != null) {
                list.add(e);
            }
        }

        if (list.isEmpty()) {
            message.setStatus(-1);
            message.setResult("未查询到相关快递信息");
            return JSONUtil.toJSON(message);
        }

        // 直接使用Express类，避免重复的BootstrapExpress类
        List<Express> resultList = new ArrayList<>();
        for (Express e : list) {
            // 创建新的Express对象，设置格式化后的字段
            Express formattedExpress = new Express();
            formattedExpress.setId(e.getId());
            formattedExpress.setNumber(e.getNumber());
            formattedExpress.setUsername(e.getUsername());
            formattedExpress.setUserPhone(e.getUserPhone());
            formattedExpress.setCompany(e.getCompany());
            formattedExpress.setCode(e.getCode() == null ? "已取件" : e.getCode());
            formattedExpress.setInTime(e.getInTime());
            formattedExpress.setOutTime(e.getOutTime());
            formattedExpress.setStatus(e.getStatus());
            formattedExpress.setSysPhone(e.getSysPhone());
            formattedExpress.setLockerId(e.getLockerId());
            formattedExpress.setSendDistrict(e.getSendDistrict());
            formattedExpress.setReceiveDistrict(e.getReceiveDistrict());
            resultList.add(formattedExpress);
        }

        message.setStatus(0);
        message.setResult("查询成功");
        message.setData(resultList);
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/wx/pickExpress.do")
    public String pickExpress(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        
        System.out.println("=== WX Controller层: 开始取出快递，取件码: " + code + " ===");
        
        Message message = new Message();
        
        if (code == null || code.trim().isEmpty()) {
            message.setStatus(-1);
            message.setResult("取件码不能为空");
            return JSONUtil.toJSON(message);
        }
        
        // 调用ExpressService的取出快递方法
        boolean result = ExpressService.pickExpressAndReleaseLocker(code);
        
        if (result) {
            message.setStatus(0);
            message.setResult("取件成功！");
            System.out.println("=== WX Controller层: 取件成功 ===");
        } else {
            message.setStatus(-1);
            message.setResult("取件失败，请检查取件码是否正确");
            System.err.println("=== WX Controller层: 取件失败 ===");
        }
        
        return JSONUtil.toJSON(message);
    }



}
