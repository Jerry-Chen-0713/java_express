package cjy.util;

import com.aliyuncs.http.HttpResponse;
import com.google.gson.Gson;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class SMSUtil {
    /*public static boolean send(String phoneNumber,String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "秘钥id", "秘钥值");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "手机号");
        request.putQueryParameter("SignName", "签名名称");
        request.putQueryParameter("TemplateCode", "短信模板code");
        request.putQueryParameter("TemplateParam", "填充的参数（JSON对象格式）");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String json = response.getData();
            Gson g = new Gson();
            HashMap result = g.fromJson(json, HashMap.class);
            if("OK".equals(result.get("Message"))) {
                return true;
            }else{
                System.out.println("短信发送失败，原因："+result.get("Message"));
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "你自己的AppCode";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", "mobile");
        querys.put("param", "**code**:12345,**minute**:5");

//smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html

        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /*
             * 重要提示如下:
             * HttpUtils请从\r\n\t    \t* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java\r\n\t    \t* 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */ /*
            HttpResponse response = (HttpResponse) HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
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

        // 请替换为实际的短信签名ID和模板ID
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
