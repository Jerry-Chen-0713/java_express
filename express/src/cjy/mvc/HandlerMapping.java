package cjy.mvc;

/**
 * @author yemage
 */

import cjy.mvc.annotation.ResponseBody;
import cjy.mvc.annotation.ResponseView;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 映射器(包含了大量的网址与方法的对应关系)
 */
public class HandlerMapping {
    
    private static Map<String, MVCMapping> data = new HashMap();
    public static MVCMapping get(String uri){
        return data.get(uri);
    }
    public static void load(InputStream is){
        Properties properties = new Properties();
        try {
            System.out.println("=== HandlerMapping: 开始加载配置文件 ===");
            properties.load(is);
            //获取配置文件中描述的一个个的类
            Collection<Object> values = properties.values();
            System.out.println("=== HandlerMapping: 找到 " + values.size() + " 个控制器类 ===");
            
            for (Object cla:values) {
                String className = (String)cla;
                System.out.println("=== HandlerMapping: 加载控制器类: " + className + " ===");
                try {
                    //加载配置文件中描述的每一个类
                    Class c = Class.forName(className);
                    //创建这个类的对象
                    Object o = c.getConstructor().newInstance();
                    System.out.println("=== HandlerMapping: 控制器实例化成功: " + className + " ===");
                    
                    //获取这个类的所有方法
                    Method[] methods = c.getMethods();
                    int methodCount = 0;
                    for (Method m:methods){
                        Annotation[] an = m.getAnnotations();
                        if(an != null){
                            for(Annotation annotation:an){
                                if(annotation instanceof ResponseBody){
                                    //说明此方法，用于返回字符串给客户端
                                    MVCMapping mapping = new MVCMapping(o,m,ResponseType.TEXT);
                                    String url = ((ResponseBody) annotation).value();
                                    Object object = data.put(url, mapping);
                                    if(object != null){
                                        //存在了重复的请求地址
                                        throw new RuntimeException("请求地址重复：" + url);
                                    }
                                    System.out.println("=== HandlerMapping: 注册URL映射: " + url + " -> " + className + "." + m.getName() + " ===");
                                    methodCount++;
                                }else if(annotation instanceof ResponseView){
                                    //说明此方法，用于返回界面给客户端
                                    MVCMapping mapping = new MVCMapping(o,m,ResponseType.VIEW);
                                    String url = ((ResponseView) annotation).value();
                                    Object object = data.put(url, mapping);
                                    if(object != null){
                                        //存在了重复的请求地址
                                        throw new RuntimeException("请求地址重复：" + url);
                                    }
                                    System.out.println("=== HandlerMapping: 注册URL映射: " + url + " -> " + className + "." + m.getName() + " ===");
                                    methodCount++;
                                }
                            }
                        }
                    }
                    System.out.println("=== HandlerMapping: 控制器 " + className + " 注册了 " + methodCount + " 个方法 ===");

                } catch (Exception e) {
                    System.err.println("=== HandlerMapping: 加载控制器类失败: " + className + " ===");
                    System.err.println("=== HandlerMapping: 异常信息: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            System.out.println("=== HandlerMapping: 配置加载完成，共注册 " + data.size() + " 个URL映射 ===");
        } catch (Exception e) {
            System.err.println("=== HandlerMapping: 配置文件加载失败 ===");
            e.printStackTrace();
        }
    }

    /**
     * 映射对象，每一个对象封装了一个方法，用于处理请求
     */
    public static class MVCMapping {
        private Object obj;
        private Method method;
        private ResponseType type;

        public MVCMapping() {
        }

        public MVCMapping(Object obj, Method method, ResponseType type) {
            this.obj = obj;
            this.method = method;
            this.type = type;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public ResponseType getType() {
            return type;
        }

        public void setType(ResponseType type) {
            this.type = type;
        }
    }
}
