package cjy.controller;

import cjy.bean.BootstrapExpress;
import cjy.bean.Express;
import cjy.bean.Message;
import cjy.bean.ResultData;
import cjy.mvc.annotation.ResponseBody;
import cjy.service.ExpressService;
import cjy.util.DateFormatUtil;
import cjy.util.JSONUtil;
import cjy.util.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpressController {

    @ResponseBody("/express/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Integer>> console = ExpressService.console();
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

    @ResponseBody("/express/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response){
        // 获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        List<Express> list = ExpressService.findAll(true, offset, pageNumber);
        List<BootstrapExpress> list2 = new ArrayList<>();
        for(Express e:list){
            String code = e.getCode() == null ? "已取件" : e.getCode();
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库":DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"待取件":"已取件";
            BootstrapExpress e2 = new BootstrapExpress(e.getId(), e.getNumber(), e.getUsername(), e.getUserPhone(), e.getCompany(), code, inTime, outTime, status, e.getSysPhone());
            list2.add(e2);
        }
        List<Map<String, Integer>> console = ExpressService.console();
        Integer total = console.get(0).get("data1_size");
        ResultData<BootstrapExpress> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    @ResponseBody("/express/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        Express express = new Express(number, username, userPhone, company, UserUtil.getUserPhone(request.getSession()));
        boolean insert = ExpressService.insert(express);
        Message message = new Message();
        if(insert){
            message.setStatus(0);
            message.setResult("快递录入成功！");
        }else{
            message.setStatus(-1);
            message.setResult("快递录入失败！");
        }
        String json = JSONUtil.toJSON(message);
        return json;
    }

    @ResponseBody("/express/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response){
        String number = request.getParameter("number");
        Express express = ExpressService.findByNumber(number);
        Message message = new Message();
        if(express != null){
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(express);
        }else{
            message.setStatus(-1);
            message.setResult("快递不存在");
        }
        String json = JSONUtil.toJSON(message);
        return json;
    }

    @ResponseBody("/express/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response){
        try {
            // 获取前端传递的参数
            String idStr = request.getParameter("id");
            String number = request.getParameter("number");
            String company = request.getParameter("company");
            String username = request.getParameter("username");
            String userPhone = request.getParameter("userPhone");
            String statusStr = request.getParameter("status");

            // 参数验证
            if(idStr == null || idStr.trim().isEmpty()) {
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("修改失败：ID不能为空");
                return JSONUtil.toJSON(message);
            }

            // 转换参数类型
            Integer id = Integer.parseInt(idStr);
            Integer status = statusStr != null ? Integer.parseInt(statusStr) : 0;

            System.out.println("修改快递信息 - ID: " + id + ", 单号: " + number +
                    ", 公司: " + company + ", 姓名: " + username +
                    ", 手机: " + userPhone + ", 状态: " + status);

            // 创建新的快递对象
            Express newExpress = new Express();
            newExpress.setNumber(number);
            newExpress.setCompany(company);
            newExpress.setUsername(username);
            newExpress.setUserPhone(userPhone);
            newExpress.setStatus(status);

            // 调用Service层进行更新（按照您的Service层逻辑）
            boolean updateResult = ExpressService.update(id, newExpress);

            Message message = new Message();
            if(updateResult){
                message.setStatus(0);
                message.setResult("修改成功！");
                System.out.println("快递修改成功，ID: " + id);
            } else {
                message.setStatus(-1);
                message.setResult("修改失败！");
                System.out.println("快递修改失败，ID: " + id);
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

    @ResponseBody("/express/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response){
        try {
            // 获取前端传递的ID参数
            String idStr = request.getParameter("id");

            // 参数验证
            if(idStr == null || idStr.trim().isEmpty()) {
                Message message = new Message();
                message.setStatus(-1);
                message.setResult("删除失败：ID不能为空");
                return JSONUtil.toJSON(message);
            }

            // 转换参数类型
            Integer id = Integer.parseInt(idStr);

            System.out.println("删除快递信息 - ID: " + id);

            // 调用Service层进行删除
            boolean deleteResult = ExpressService.delete(id);

            Message message = new Message();
            if(deleteResult){
                message.setStatus(0);
                message.setResult("删除成功！");
                System.out.println("快递删除成功，ID: " + id);
            } else {
                message.setStatus(-1);
                message.setResult("删除失败！快递不存在或已被删除");
                System.out.println("快递删除失败，ID: " + id);
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

    @ResponseBody("/express/rank.do")
    public String rank(HttpServletRequest request, HttpServletResponse response) {
        String period = request.getParameter("period");
        if (period == null || period.trim().isEmpty()) {
            period = "total";
        }
        // 直接用 ExpressService.findAll 查询所有快递，统计 username 数量
        List<Express> list = ExpressService.findAll(true, 0, Integer.MAX_VALUE);
        Map<String, Integer> countMap = new java.util.HashMap<>();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        for (Express e : list) {
            boolean match = true;
            if ("year".equals(period)) {
                cal.setTime(e.getInTime());
                match = cal.get(java.util.Calendar.YEAR) == java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
            } else if ("month".equals(period)) {
                cal.setTime(e.getInTime());
                java.util.Calendar now = java.util.Calendar.getInstance();
                match = cal.get(java.util.Calendar.YEAR) == now.get(java.util.Calendar.YEAR)
                        && cal.get(java.util.Calendar.MONTH) == now.get(java.util.Calendar.MONTH);
            }
            if (match) {
                String username = e.getUsername();
                countMap.put(username, countMap.getOrDefault(username, 0) + 1);
            }
        }
        // 排序
        List<Map<String, Object>> rankList = new ArrayList<>();
        countMap.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(100)
                .forEach(entry -> {
                    Map<String, Object> m = new java.util.HashMap<>();
                    m.put("username", entry.getKey());
                    m.put("count", entry.getValue());
                    m.put("avatar", "images/userHeadImg.jpg");
                    rankList.add(m);
                });
        Message message = new Message();
        if (rankList.isEmpty()) {
            message.setStatus(-1);
        } else {
            message.setStatus(0);
        }
        message.setData(rankList);
        return JSONUtil.toJSON(message);
    }
}