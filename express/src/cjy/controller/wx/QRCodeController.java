package cjy.controller.wx;

import cjy.bean.Express;
import cjy.bean.Message;
import cjy.bean.User;
import cjy.mvc.annotation.ResponseBody;
import cjy.mvc.annotation.ResponseView;
import cjy.service.ExpressService;
import cjy.util.DateFormatUtil;
import cjy.util.JSONUtil;
import cjy.util.UserUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class QRCodeController {

    @ResponseView("/wx/createQRCode.do")
    public String createQrCode(HttpServletRequest request, HttpServletResponse response){

        String code = request.getParameter("code");
        String type = request.getParameter("type");
        String userPhone = null;
        String qRCodeContent = null;

        if("express".equals(type)){
            qRCodeContent = "express_" + code;
        }else{
            // ★★★★★ 这是您需要修改的地方 ★★★★★
            User wxUser = UserUtil.getLoginUser(request.getSession());

            if (wxUser != null) {
                userPhone = wxUser.getUserPhone();
                qRCodeContent = "userPhone_" + userPhone ;
            } else {
                // 如果用户未登录，返回到登录页面
                return "/login.html";
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("qrcode",qRCodeContent);
        return "/personQRcode.html";

    }


    @ResponseBody("/wx/getQRCodeData.do")
    public String getQRCodeData(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");

        Message message = new Message();

        try {
            User user = UserUtil.getLoginUser(request.getSession());

            if (user != null && user.getUserPhone() != null) {
                message.setStatus(0);
                message.setResult(user.getUserPhone());
            } else {
                message.setStatus(-1);
                message.setResult("用户未登录，无法生成二维码");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setStatus(-1);
            message.setResult("服务器内部错误：" + e.getMessage());
        }

        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/wx/updateStatus.do")
    public String updateStatus(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        
        boolean status = ExpressService.updateStatus(code);
        Message message = new Message();
        if(status){
            message.setStatus(0);
            message.setResult("取件成功");
        }else{
            message.setStatus(-1);
            message.setResult("取件码不存在，请用户更新二维码");
        }
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/wx/pickExpress.do")
    public String pickExpress(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        System.out.println("=== QRCodeController: 开始取出快递并释放快递柜，取件码: " + code + " ===");
        
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        
        boolean result = ExpressService.pickExpressAndReleaseLocker(code);
        Message message = new Message();
        if(result){
            message.setStatus(0);
            message.setResult("取件成功，快递柜已释放");
            System.out.println("=== QRCodeController: 取出快递并释放快递柜成功 ===");
        }else{
            message.setStatus(-1);
            message.setResult("取件失败，请检查取件码是否正确");
            System.err.println("=== QRCodeController: 取出快递并释放快递柜失败 ===");
        }
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/wx/findExpressByNumber.do")
    public String findExpressByNumber(HttpServletRequest request, HttpServletResponse response){
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        
        String number = request.getParameter("number");
        System.out.println("=== QRCodeController: 根据快递单号查询，单号: " + number + " ===");
        
        Express express = ExpressService.findByNumber(number);
        Express formattedExpress = null;
        if(express != null){
            // 创建新的Express对象，设置格式化后的字段
            formattedExpress = new Express();
            formattedExpress.setId(express.getId());
            formattedExpress.setNumber(express.getNumber());
            formattedExpress.setUsername(express.getUsername());
            formattedExpress.setUserPhone(express.getUserPhone());
            formattedExpress.setCompany(express.getCompany());
            formattedExpress.setCode(express.getCode() == null ? "已取件" : express.getCode());
            formattedExpress.setInTime(express.getInTime());
            formattedExpress.setOutTime(express.getOutTime());
            formattedExpress.setStatus(express.getStatus());
            formattedExpress.setSysPhone(express.getSysPhone());
            formattedExpress.setLockerId(express.getLockerId());
            formattedExpress.setSendDistrict(express.getSendDistrict());
            formattedExpress.setReceiveDistrict(express.getReceiveDistrict());
        }
        
        Message message = new Message();
        if(express == null){
            message.setStatus(-1);
            message.setResult("未找到该快递单号的信息");
            System.err.println("=== QRCodeController: 未找到快递单号: " + number + " ===");
        }else{
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(formattedExpress);
            System.out.println("=== QRCodeController: 找到快递信息，取件码: " + express.getCode() + " ===");
        }
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/wx/findExpressByCode.do")
    public String findExpressByCode(HttpServletRequest request,HttpServletResponse response){
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        
        String code = request.getParameter("code");
        Express express = ExpressService.findByCode(code);
        Express formattedExpress = null;
        if(express != null){
            // 创建新的Express对象，设置格式化后的字段
            formattedExpress = new Express();
            formattedExpress.setId(express.getId());
            formattedExpress.setNumber(express.getNumber());
            formattedExpress.setUsername(express.getUsername());
            formattedExpress.setUserPhone(express.getUserPhone());
            formattedExpress.setCompany(express.getCompany());
            formattedExpress.setCode(express.getCode() == null ? "已取件" : express.getCode());
            formattedExpress.setInTime(express.getInTime());
            formattedExpress.setOutTime(express.getOutTime());
            formattedExpress.setStatus(express.getStatus());
            formattedExpress.setSysPhone(express.getSysPhone());
            formattedExpress.setLockerId(express.getLockerId());
            formattedExpress.setSendDistrict(express.getSendDistrict());
            formattedExpress.setReceiveDistrict(express.getReceiveDistrict());
        }
        Message message = new Message();
        if(express == null){
            message.setStatus(-1);
            message.setResult("取件码不存在");
        }else{
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(formattedExpress);
        }
        return JSONUtil.toJSON(message);
    }
}
