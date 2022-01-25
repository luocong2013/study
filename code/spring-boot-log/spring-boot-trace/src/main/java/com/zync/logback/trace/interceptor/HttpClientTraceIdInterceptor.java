package com.zync.logback.trace.interceptor;

import com.zync.logback.trace.common.Const;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * 添加HttpClient拦截器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/24 14:46
 */
public class HttpClientTraceIdInterceptor implements HttpRequestInterceptor {

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        String traceId = MDC.get(Const.MDC_TRACE_ID);
        // 当前线程调用中有traceId，则将该traceId进行透传
        if (StringUtils.isNotBlank(traceId)) {
            request.addHeader(Const.MDC_TRACE_ID, traceId);
        }
    }
}
