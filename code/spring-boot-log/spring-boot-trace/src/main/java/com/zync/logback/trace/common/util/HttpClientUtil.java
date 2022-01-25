package com.zync.logback.trace.common.util;

import com.zync.logback.trace.interceptor.HttpClientTraceIdInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * httpClient工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/24 14:31
 */
@Slf4j
public final class HttpClientUtil {

    private final static CloseableHttpClient HTTP_CLIENT = HttpClientBuilder.create()
            .addInterceptorFirst(new HttpClientTraceIdInterceptor())
            .build();

    /**
     * GET请求
     * @param httpUrl 请求地址
     * @return
     */
    public static String doGet(String httpUrl) {
        CloseableHttpResponse response = null;
        String result = null;
        // 创建HttpGet远程连接实例
        HttpGet httpGet = new HttpGet(httpUrl);
        // 设置配置请求参数：连接主机服务超时时间、请求超时时间和数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(60000)
                .build();
        // 为HttpGet实例设置配置
        httpGet.setConfig(requestConfig);
        try {
            // 执行GET请求得到返回对象
            response = HTTP_CLIENT.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            log.error("执行GET请求异常", e);
        } finally {
            IOUtils.closeQuietly(response);
        }
        return result;
    }

    private HttpClientUtil() {}
}
