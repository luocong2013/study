package com.mountain.common.common;


/**
 * 常量接口
 *
 * @author luocong
 * @version v1.0
 * @since 2023/10/31 15:42
 */
public interface Const {
    /**
     * mdc trace id
     */
    String MDC_TRACE_ID = "X-Trace-Id";
    /**
     * unknown
     */
    String UNKNOWN = "unknown";
    /**
     * 字符串逗号 英文
     */
    String COMMA = ",";
    /**
     * 字符串逗号 中文
     */
    String COMMA_CH = "，";
    /**
     * 字符串分号 英文
     */
    String SEMICOLON = ";";
    /**
     * 字符串分号 中文
     */
    String SEMICOLON_CH = "；";
    /**
     * UTF-8编码
     */
    String DEFAULT_CHARACTER_ENCODING = "UTF-8";
    /**
     * 全局系统异常信息
     */
    String GLOBAL_EXCEPTION_MESSAGE = "服务器开小差了，请稍后重试！";

}
