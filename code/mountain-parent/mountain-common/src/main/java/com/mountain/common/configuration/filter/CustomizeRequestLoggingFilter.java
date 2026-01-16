package com.mountain.common.configuration.filter;

import com.mountain.common.common.ApiResponseEntity;
import com.mountain.common.common.Const;
import com.mountain.common.configuration.wrapper.MultiReadHttpServletRequestWrapper;
import com.mountain.common.configuration.wrapper.MultiReadHttpServletResponseWrapper;
import com.mountain.common.utils.IpUtil;
import com.mountain.common.utils.LogTraceIdUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.MDC;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 自定义 添加traceId 和 请求日志 Filter
 *
 * @author luocong
 * @version v1.0
 * @since 2023/9/4 14:58
 */
@Slf4j
@Component
@Order(OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER - 6000)
public class CustomizeRequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        addTraceId(request, response);
        log.info("Request come in [{} {}, client={}, headers={}]", request.getMethod(), request.getRequestURI(), IpUtil.getIpAddress(request), headers(request));
        StopWatch watch = StopWatch.createStarted();
        MultiReadHttpServletRequestWrapper wrappedRequest = new MultiReadHttpServletRequestWrapper(request);
        MultiReadHttpServletResponseWrapper wrappedResponse = new MultiReadHttpServletResponseWrapper(response);
        try {
            // 记录请求的消息体
            logRequestBody(wrappedRequest);
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } catch (Exception e) {
            ApiResponseEntity.out(wrappedResponse, e);
        } finally {
            watch.stop();
            // 记录响应的消息体
            logResponseBody(wrappedRequest, wrappedResponse, watch.getTime(TimeUnit.MILLISECONDS));
            // 将缓存的返回数据写入输出流，否则返回结果会一直为空
            wrappedResponse.copyBodyToResponse();
            MDC.clear();
        }
    }

    /**
     * 记录请求的消息体
     *
     * @param request
     */
    private void logRequestBody(MultiReadHttpServletRequestWrapper request) {
        if (request == null) {
            return;
        }
        try {
            String parameterMap = request.getBodyJsonStrByForm(request);
            String payload = request.getBodyJsonStrByJson(request);
            log.info("\n-------------------------------- request url: {} --------------------------------\nparameterMap: {}\npayload: {}", request.getRequestURI(), parameterMap, payload);
        } catch (Exception e) {
            log.error("记录请求消息错误", e);
        }
    }

    /**
     * 记录响应的消息体
     *
     * @param request
     * @param response
     * @param useTime
     */
    private void logResponseBody(MultiReadHttpServletRequestWrapper request, MultiReadHttpServletResponseWrapper response, long useTime) {
        if (response == null) {
            return;
        }
        byte[] buf = response.getContentAsByteArray();
        if (buf.length > 0) {
            String payload;
            try {
                payload = new String(buf, response.getCharacterEncoding());
            } catch (UnsupportedEncodingException ex) {
                payload = "[unknown]";
            }
            log.info("`{}` costTime: {} ms response: {}", request.getRequestURI(), useTime, payload);
        }
    }

    /**
     * add traceId
     *
     * @param request
     * @param response
     */
    private void addTraceId(HttpServletRequest request, HttpServletResponse response) {
        String traceId = request.getHeader(Const.MDC_TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = LogTraceIdUtil.generateTraceId();
        }
        MDC.put(Const.MDC_TRACE_ID, traceId);
        response.addHeader(Const.MDC_TRACE_ID, traceId);
    }

    /**
     * 获取所有请求头信息
     *
     * @param request
     * @return
     */
    private Map<String, String> headers(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>(16);
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            headerMap.put(name, value);
        }
        return headerMap;
    }
}
