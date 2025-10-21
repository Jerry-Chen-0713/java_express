package cjy.controller.wx;

import cjy.bean.BootstrapExpress;
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

    @ResponseBody("/wx/createQRCode.do")
    public String createQrCode(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        String type = request.getParameter("type");
        String qRCodeContent = null;
        if("express".equals(type)){
            qRCodeContent = "express_" + code;
        }else{
            User wxUser = UserUtil.getLoginUser(request.getSession());
            if (wxUser == null) {
                Message message = new Message("用户未登录，无法生成二维码", -1);
                return JSONUtil.toJSON(message);
            }
            String userPhone = wxUser.getUserPhone();
            qRCodeContent = "userPhone_" + userPhone ;
        }
        HttpSession session = request.getSession();
        session.setAttribute("qrcode",qRCodeContent);

        Message message = new Message("二维码信息已准备好", 0);
        return JSONUtil.toJSON(message);
    }


    @ResponseBody("/wx/qrcode.do")
    public String getQRCode(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        String qrcode = (String) session.getAttribute("qrcode");

        Message message = new Message();
        if(qrcode == null){
            message.setStatus(-1);
            message.setResult("取件码获取出错，请用户重新操作！");
        }else{
            message.setStatus(0);
            message.setResult(qrcode);
        }
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/wx/updateStatus.do")
    public String updateStatus(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
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

    @ResponseBody("/wx/findExpressByCode.do")
    public String findExpressByCode(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        Express express = ExpressService.findByCode(code);
        BootstrapExpress express2 = null;
        if(express != null){
            express2 = new BootstrapExpress(express.getId(),express.getNumber(), express.getUsername(), express.getUserPhone(),express.getCompany(), express.getCode(), DateFormatUtil.format(express.getInTime()),express.getOutTime()==null?"未出库":DateFormatUtil.format(express.getOutTime()), express.getStatus()==0?"待取件":"已取件",express.getSysPhone());
        }
        Message message = new Message();
        if(express == null){
            message.setStatus(-1);
            message.setResult("取件码不存在");
        }else{
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(express2);
        }
        return JSONUtil.toJSON(message);
    }
}