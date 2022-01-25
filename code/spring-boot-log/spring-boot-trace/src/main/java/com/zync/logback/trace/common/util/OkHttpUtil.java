package com.zync.logback.trace.common.util;

import com.zync.logback.trace.common.Const;
import com.zync.logback.trace.interceptor.OkHttpTraceIdInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/25 09:50
 */
@Slf4j
public final class OkHttpUtil {

    private final static OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(new OkHttpTraceIdInterceptor())
            .build();

    /**
     * GET请求
     * @param httpUrl 请求地址
     * @return
     */
    public static String doGet(String httpUrl) {
        try {
            long start = System.currentTimeMillis();
            Request request = new Request.Builder().url(httpUrl).build();
            requestLog(request, Const.HTTP_METHOD_GET, httpUrl, null, null);
            Call call = CLIENT.newCall(request);
            Response response = call.execute();
            ResponseBody body = response.body();
            if (Objects.nonNull(body)) {
                String result = body.string();
                responseLog(response, response.code(), result, start);
                return result;
            }
        } catch (IOException e) {
            log.error("OkHttp执行GET请求异常", e);
        }
        return "";
    }

    /**
     * 请求开始日志
     * @param request
     * @param method
     * @param url
     * @param body
     * @param formParam
     */
    private static void requestLog(Request request, String method, String url, String body, String formParam) {
        log.info("===========================request begin================================================");
        log.info("URI          : {}", url);
        log.info("Method       : {}", method);
        log.info("Headers      : {}", header(request.headers()));
        if (StringUtils.isNotBlank(body)) {
            log.info("Request body : {}", body);
        }
        if (StringUtils.isNotBlank(formParam)) {
            log.info("Request param: {}", formParam);
        }
        log.info("---------------------------request end--------------------------------------------------");
    }

    /**
     * 请求返回日志
     * @param response
     * @param httpCode
     * @param result
     * @param startTime
     */
    private static void responseLog(Response response, int httpCode, String result, long startTime) {
        long endTime = System.currentTimeMillis();
        log.info("Status       : {}", httpCode);
        log.info("Headers      : {}", header(response.headers()));
        log.info("Response     : {}", result);
        log.info("Time         : {} ms", endTime - startTime);
        log.info("===========================response end================================================");
    }

    /**
     * header
     * @param headers
     * @return
     */
    private static String header(Headers headers) {
        StringBuilder builder = new StringBuilder();
        int size = headers.size();
        builder.append("[");
        for (int i = 0; i < size; i++) {
            String name = headers.name(i);
            builder.append(name).append("=").append(headers.get(name)).append("  ");
        }
        builder.append("]");
        return builder.toString();
    }

    private OkHttpUtil() {}
}
