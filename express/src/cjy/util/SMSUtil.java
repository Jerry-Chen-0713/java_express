package cjy.util;

import com.aliyuncs.http.HttpResponse;
import com.google.gson.Gson;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class SMSUtil {
    public static boolean send(String phoneNumber, String code) {
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "0e41a38d09094fc18ec31c1f5e18e54c";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phoneNumber);
        querys.put("param", "**code**:" + code + ",**minute**:5");

        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");

        Map<String, String> bodys = new HashMap<String, String>();

        try {
            HttpResponse response = (HttpResponse) HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());

            // 解析响应结果
            String responseBody = EntityUtils.toString(((org.apache.http.HttpResponse) response).getEntity());
            System.out.println(responseBody);

            // 根据实际API返回的JSON格式进行解析
            Gson g = new Gson();
            HashMap result = g.fromJson(responseBody, HashMap.class);

            // 根据实际API的成功标识进行调整
            if("00000".equals(result.get("code"))) { // 请根据实际API文档调整成功码
                return true;
            } else {
                System.out.println("短信发送失败，原因：" + result.get("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
