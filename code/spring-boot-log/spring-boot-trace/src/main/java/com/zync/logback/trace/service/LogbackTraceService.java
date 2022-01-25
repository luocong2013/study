package com.zync.logback.trace.service;

/**
 * logback trace 服务接口
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/25 11:01
 */
public interface LogbackTraceService {

    /**
     * httpClient执行GET请求
     * @param httpUrl
     * @return
     */
    String httpClient(String httpUrl);

    /**
     * restTemplate执行GET请求
     * @param httpUrl
     * @return
     */
    String restTemplate(String httpUrl);

    /**
     * okHttp执行GET请求
     * @param httpUrl
     * @return
     */
    String okHttp(String httpUrl);
}
