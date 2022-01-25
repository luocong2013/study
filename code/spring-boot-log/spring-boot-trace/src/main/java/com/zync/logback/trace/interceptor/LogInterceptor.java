package com.zync.logback.trace.interceptor;

import com.zync.logback.trace.common.Const;
import com.zync.logback.trace.common.util.LogTraceHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MDC日志记录拦截器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/24 12:02
 */
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果有上层调用就用上层的TraceId
        String traceId = request.getHeader(Const.MDC_TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = LogTraceHelper.getTraceId();
        }
        MDC.put(Const.MDC_TRACE_ID, traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 调用结束后删除
        MDC.remove(Const.MDC_TRACE_ID);
    }
}
