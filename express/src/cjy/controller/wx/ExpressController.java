package cjy.controller.wx;

import cjy.bean.BootstrapExpress;
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
            User loginUser = UserUtil.getLoginUser(request.getSession());
            if (loginUser == null) {
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("用户未登录");
                return JSONUtil.toJSON(message);
            }
            if (loginUser.getUserPhone() == null) {
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("用户信息不完整");
                return JSONUtil.toJSON(message);
            }
            String userPhone = loginUser.getUserPhone();
            List<Express> list = ExpressService.findByUserPhone(userPhone);
            List<BootstrapExpress> list2 = new ArrayList<>();
            for(Express e:list){
                String code = e.getCode() == null?"已取件":e.getCode();
                String inTime = DateFormatUtil.format(e.getInTime());
                String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
                String status = e.getStatus()==0?"待取件":"已取件";
                BootstrapExpress e2 = new BootstrapExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
                list2.add(e2);
            }
            Message message = new Message();
            if(list.size() == 0){
                message.setStatus(-1);
                message.setResult("暂无快递信息");
                message.setData(new ArrayList<>());
            } else {
                message.setStatus(0);
                message.setResult("查询成功");
                Stream<BootstrapExpress> expressStream1 = list2.stream().filter(express -> "待取件".equals(express.getStatus())).sorted((o1, o2) -> Long.compare(DateFormatUtil.toTime(o1.getInTime()), DateFormatUtil.toTime(o2.getInTime())));
                Stream<BootstrapExpress> expressStream2 = list2.stream().filter(express -> "已取件".equals(express.getStatus())).sorted((o1, o2) -> Long.compare(DateFormatUtil.toTime(o1.getInTime()), DateFormatUtil.toTime(o2.getInTime())));
                Object[] array1 = expressStream1.toArray();
                Object[] array2 = expressStream2.toArray();
                Map<String, Object> data = new HashMap<>();
                data.put("status1",array1);
                data.put("status2",array2);
                message.setData(data);
            }
            return JSONUtil.toJSON(message);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("系统错误: " + e.getMessage());
            return JSONUtil.toJSON(message);
        }
    }

    @ResponseBody("/wx/userExpressList.do")
    public String expressList(HttpServletRequest request,HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        List<Express> list = ExpressService.findByUserPhoneAndStatus(userPhone, 0);
        List<BootstrapExpress> list2 = new ArrayList<>();
        for(Express e:list){
            String code = e.getCode() == null?"已取件":e.getCode();
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"待取件":"已取件";
            BootstrapExpress e2 = new BootstrapExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }
        Message message = new Message();
        if(list.size() == 0){
            message.setStatus(-1);
            message.setResult("未查询到的快递");
        }else{
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(list2);
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
        List<BootstrapExpress> resultList = new ArrayList<>();
        for (Express e : list) {
            String code = e.getCode() == null ? "已取件" : e.getCode();
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime() == null ? "未出库" : DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            BootstrapExpress e2 = new BootstrapExpress(e.getId(), e.getNumber(), e.getUsername(), e.getUserPhone(), e.getCompany(), code, inTime, outTime, status, e.getSysPhone());
            resultList.add(e2);
        }
        message.setStatus(0);
        message.setResult("查询成功");
        message.setData(resultList);
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtil.getLoginUser(request.getSession());
        Message message = new Message();
        if (user != null && user.isUser()) {
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(user);
        } else {
            message.setStatus(-1);
            message.setResult("非普通用户或未登录");
        }
        return JSONUtil.toJSON(message);
    }
}