package com.zync.logback.trace.interceptor;

import com.zync.logback.trace.common.Const;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * OkHttp添加traceId拦截器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/25 09:31
 */
public class OkHttpTraceIdInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String traceId = MDC.get(Const.MDC_TRACE_ID);
        Request.Builder builder = chain.request().newBuilder();
        if (StringUtils.isNotBlank(traceId)) {
            builder.addHeader(Const.MDC_TRACE_ID, traceId);
        }
        return chain.proceed(builder.build());
    }
}
