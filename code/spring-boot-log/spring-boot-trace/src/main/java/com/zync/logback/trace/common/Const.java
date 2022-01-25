package com.zync.logback.trace.common;

/**
 * 常量接口
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/24 12:00
 */
public interface Const {

    /**
     * mdc trace id
     */
    String MDC_TRACE_ID = "traceId";

    /**
     * 请求地址
     */
    String HTTP_URL = "https://www.cuit.edu.cn/";

    /**
     * GET请求方式
     */
    String HTTP_METHOD_GET = "GET";
}
