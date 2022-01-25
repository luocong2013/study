package com.zync.logback.trace.common.util;

import com.zync.logback.trace.interceptor.RestTemplateTraceIdInterceptor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * RestTemplate工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/25 09:46
 */
public final class RestTemplateUtil {

    private final static RestTemplate REST_TEMPLATE = new RestTemplate();

    static {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new RestTemplateTraceIdInterceptor());
        REST_TEMPLATE.setInterceptors(interceptors);
    }

    /**
     * GET请求
     * @param httpUrl 请求地址
     * @return
     */
    public static String doGet(String httpUrl) {
        return REST_TEMPLATE.getForObject(httpUrl, String.class);
    }

    private RestTemplateUtil() {}
}
