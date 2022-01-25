package com.zync.logback.trace.interceptor;

import com.zync.logback.trace.common.Const;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * RestTemplate添加traceId拦截器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/24 14:49
 */
public class RestTemplateTraceIdInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String traceId = MDC.get(Const.MDC_TRACE_ID);
        if (StringUtils.isNotBlank(traceId)) {
            request.getHeaders().add(Const.MDC_TRACE_ID, traceId);
        }
        return execution.execute(request, body);
    }
}
