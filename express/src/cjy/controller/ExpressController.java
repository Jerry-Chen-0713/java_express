package cjy.controller;

import cjy.bean.Express;
import cjy.bean.Message;
import cjy.bean.ResultData;
import cjy.mvc.annotation.ResponseBody;
import cjy.service.ExpressService;
import cjy.service.LockerService;
import cjy.util.DateFormatUtil;
import cjy.util.JSONUtil;
import cjy.util.RandomUtil;
import cjy.util.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
        // 设置响应编码，确保中文正确显示
        response.setContentType("application/json;charset=UTF-8");
        
        // 获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        List<Express> list = ExpressService.findAll(true, offset, pageNumber);
        
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
        
        List<Map<String, Integer>> console = ExpressService.console();
        Integer total = console.get(0).get("data1_size");
        ResultData<Express> data = new ResultData<>();
        data.setRows(formattedList);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    @ResponseBody("/express/checkLocker.do")
    public String checkLocker(HttpServletRequest request, HttpServletResponse response){
        String district = request.getParameter("district");
        String size = request.getParameter("size");
        
        Message message = new Message();
        if (district == null || size == null) {
            message.setStatus(-1);
            message.setResult("参数不完整");
        } else {
            boolean isFull = LockerService.isLockerFull(district, size);
            if (isFull) {
                message.setStatus(-1);
                message.setResult("快递柜已满");
            } else {
                message.setStatus(0);
                message.setResult("快递柜有空位");
            }
        }
        String json = JSONUtil.toJSON(message);
        return json;
    }

    @ResponseBody("/express/initLockers.do")
    public String initLockers(HttpServletRequest request, HttpServletResponse response){
        try {
            LockerService.initLockers();
            Message message = new Message();
            message.setStatus(0);
            message.setResult("快递柜数据初始化成功");
            return JSONUtil.toJSON(message);
        } catch (Exception e) {
            Message message = new Message();
            message.setStatus(-1);
            message.setResult("快递柜数据初始化失败: " + e.getMessage());
            return JSONUtil.toJSON(message);
        }
    }

    @ResponseBody("/express/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        // 设置请求和响应编码
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("application/json;charset=UTF-8");
        
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        String sendDistrict = request.getParameter("send_district");
        String receiveDistrict = request.getParameter("receive_district");
        String lockerSize = request.getParameter("locker_size");
        String routePath = request.getParameter("route_path");
        String totalDistanceStr = request.getParameter("total_distance");
        
        System.out.println("=== Controller层: 开始录入快递 ===");
        System.out.println("=== Controller层: 参数 - number=" + number + ", company=" + company + 
                         ", username=" + username + ", userPhone=" + userPhone + 
                         ", sendDistrict=" + sendDistrict + ", receiveDistrict=" + receiveDistrict + 
                         ", routePath=" + routePath + ", totalDistance=" + totalDistanceStr);
        
        // 使用默认构造函数，然后设置字段
        Express express = new Express();
        express.setNumber(number);
        express.setUsername(username);
        express.setUserPhone(userPhone);
        express.setCompany(company);
        express.setSysPhone("18888888888"); // 使用UserUtil中的默认手机号
        express.setLockerId(null); // 快递录入时不分配快递柜
        express.setSendDistrict(sendDistrict);
        express.setReceiveDistrict(receiveDistrict);
        
        // 设置路径和距离信息
        express.setRoutePath(routePath);
        if (totalDistanceStr != null && !totalDistanceStr.trim().isEmpty()) {
            try {
                express.setTotalDistance(Double.parseDouble(totalDistanceStr));
            } catch (NumberFormatException e) {
                System.err.println("=== Controller层: 距离格式错误 ===");
                express.setTotalDistance(0.0);
            }
        } else {
            express.setTotalDistance(0.0);
        }
        
        // 生成随机取件码
        String code = RandomUtil.getCode() + "";
        express.setCode(code);
        
        System.out.println("=== Controller层: 创建Express对象 ===");
        System.out.println("=== Controller层: number=" + number + ", username=" + username + 
                         ", userPhone=" + userPhone + ", company=" + company + 
                         ", sysPhone=" + UserUtil.getUserPhone(request.getSession()) + 
                         ", lockerId=null, sendDistrict=" + sendDistrict + 
                         ", receiveDistrict=" + receiveDistrict + ", routePath=" + routePath + 
                         ", totalDistance=" + express.getTotalDistance() + ", code=" + code);
        
        Message message = new Message();
        try {
            boolean insert = ExpressService.insert(express);
            System.out.println("=== Controller层: 快递录入结果 - " + insert);
            
            if(insert){
                message.setStatus(0);
                message.setResult("快递录入成功！");
            }else{
                message.setStatus(-1);
                message.setResult("快递录入失败！");
            }
        } catch (cjy.exception.DuplicateCodeException e) {
            System.err.println("=== Controller层: 取件码重复异常 ===");
            e.printStackTrace();
            message.setStatus(-1);
            message.setResult("取件码重复，请重新生成！");
        }
        
        String json = JSONUtil.toJSON(message);
        return json;
    }

    @ResponseBody("/express/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response){
        // 设置响应编码，确保中文正确显示
        response.setContentType("application/json;charset=UTF-8");
        
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
        // 设置请求和响应编码
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("application/json;charset=UTF-8");
        
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
        // 设置响应编码，确保中文正确显示
        response.setContentType("application/json;charset=UTF-8");
        
        String period = request.getParameter("period");
        if (period == null || period.trim().isEmpty()) {
            period = "total";
        }
        
        System.out.println("=== Controller层: 开始加载排行榜，周期: " + period + " ===");
        
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
                // 调试输出用户名
                System.out.println("=== Controller层: 处理快递 - 用户名: " + username + " ===");
                countMap.put(username, countMap.getOrDefault(username, 0) + 1);
            }
        }
        
        // 调试输出统计结果
        System.out.println("=== Controller层: 统计结果 ===");
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println("用户名: " + entry.getKey() + ", 快递数量: " + entry.getValue());
        }
        
        // 排序
        List<Map<String, Object>> rankList = new java.util.ArrayList<>();
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
        
        // 调试输出排行榜结果
        System.out.println("=== Controller层: 排行榜结果 ===");
        for (int i = 0; i < Math.min(rankList.size(), 5); i++) {
            Map<String, Object> item = rankList.get(i);
            System.out.println("排名 " + (i+1) + ": " + item.get("username") + " - " + item.get("count") + "件");
        }
        
        Message message = new Message();
        if (rankList.isEmpty()) {
            message.setStatus(-1);
        } else {
            message.setStatus(0);
        }
        message.setData(rankList);
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/express/intoLocker.do")
    public String intoLocker(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        
        String expressNumber = request.getParameter("expressNumber");
        String lockerSize = request.getParameter("lockerSize");
        
        System.out.println("=== Controller层: 开始快递入柜 ===");
        System.out.println("=== Controller层: 参数 - expressNumber=" + expressNumber + ", lockerSize=" + lockerSize);
        
        Message message = new Message();
        
        // 检查快递号是否存在
        Express express = ExpressService.findByNumber(expressNumber);
        if (express == null) {
            message.setStatus(-1);
            message.setResult("快递单号不存在，请检查输入是否正确");
            System.err.println("=== Controller层: 快递单号不存在: " + expressNumber + " ===");
            return JSONUtil.toJSON(message);
        }
        
        // 检查快递是否已经入柜
        if (express.getLockerId() != null && express.getLockerId() > 0) {
            message.setStatus(-1);
            message.setResult("该快递已经入柜，柜号: " + express.getLockerId());
            System.err.println("=== Controller层: 快递已入柜，柜号: " + express.getLockerId() + " ===");
            return JSONUtil.toJSON(message);
        }
        
        // 分配快递柜
        Integer lockerId = LockerService.assignLocker(express.getReceiveDistrict(), lockerSize, expressNumber);
        System.out.println("=== Controller层: 分配快递柜结果 - lockerId=" + lockerId);
        
        if (lockerId == null) {
            message.setStatus(-1);
            message.setResult("该区域没有可用的" + lockerSize + "号快递柜，请选择其他容量");
            System.err.println("=== Controller层: 没有可用快递柜 ===");
            return JSONUtil.toJSON(message);
        }
        
        // 更新快递的柜子信息
        boolean updateResult = ExpressService.updateLockerId(express.getId(), lockerId);
        if (updateResult) {
            message.setStatus(0);
            message.setResult("快递成功入柜");
            message.setData(new java.util.HashMap<String, Object>() {{
                put("lockerId", lockerId);
                put("expressNumber", expressNumber);
            }});
            System.out.println("=== Controller层: 快递入柜成功，柜号: " + lockerId + " ===");
        } else {
            message.setStatus(-1);
            message.setResult("快递入柜失败，请重试");
            System.err.println("=== Controller层: 快递入柜失败 ===");
        }
        
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/express/lockerStats.do")
    public String lockerStats(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        
        String district = request.getParameter("district");
        
        System.out.println("=== Controller层: 查询快递柜统计数据 ===");
        System.out.println("=== Controller层: 参数 - district=" + district);
        
        Message message = new Message();
        
        if (district == null || district.trim().isEmpty()) {
            message.setStatus(-1);
            message.setResult("请选择区域");
            System.err.println("=== Controller层: 区域参数为空 ===");
            return JSONUtil.toJSON(message);
        }
        
        try {
            List<Map<String, Object>> stats = LockerService.getLockerStats(district);
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(stats);
            System.out.println("=== Controller层: 快递柜统计数据查询成功 ===");
        } catch (Exception e) {
            message.setStatus(-1);
            message.setResult("查询失败: " + e.getMessage());
            System.err.println("=== Controller层: 快递柜统计数据查询失败 ===");
            e.printStackTrace();
        }
        
        return JSONUtil.toJSON(message);
    }

    @ResponseBody("/express/getRegionStats.do")
    public String getRegionStats(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应编码
        response.setContentType("application/json;charset=UTF-8");
        
        System.out.println("=== Controller层: 查询快递区域统计数据 ===");
        
        Message message = new Message();
        
        try {
            List<Map<String, Object>> regionStats = ExpressService.getRegionStats();
            message.setStatus(0);
            message.setResult("查询成功");
            message.setData(regionStats);
            System.out.println("=== Controller层: 快递区域统计数据查询成功 ===");
        } catch (Exception e) {
            message.setStatus(-1);
            message.setResult("查询失败: " + e.getMessage());
            System.err.println("=== Controller层: 快递区域统计数据查询失败 ===");
            e.printStackTrace();
        }
        
        return JSONUtil.toJSON(message);
    }
}
