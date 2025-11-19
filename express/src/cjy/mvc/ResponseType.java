package cjy.mvc;

/**
 * 响应类型枚举
 * 用于标识控制器方法的响应方式
 */
public enum ResponseType {
    /**
     * 文本响应 - 对应@ResponseBody注解
     * 方法返回的内容会以文字形式直接返回到客户端
     */
    TEXT,

    /**
     * 视图响应 - 对应@ResponseView注解
     * 方法返回的内容会作为重定向路径，进行页面跳转
     */
    VIEW
}
