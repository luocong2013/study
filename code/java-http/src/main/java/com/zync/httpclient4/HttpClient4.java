package com.zync.httpclient4;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 使用HttpClient4.5实现HTTP请求
 * @date 2019/6/16 15:09
 */
public final class HttpClient4 {

    private HttpClient4() {
    }

    /**
     * GET请求
     * @param httpUrl 请求地址
     * @return
     */
    public static String doGet(String httpUrl) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = null;
        // 通过默认配置创建一个HttpClient实例
        httpClient = HttpClients.createDefault();
        // 创建HttpGet远程连接实例
        HttpGet httpGet = new HttpGet(httpUrl);
        // 设置请求头信息，鉴权
        httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
        // 设置配置请求参数：连接主机服务超时时间、请求超时时间和数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(60000)
                .build();
        // 为HttpGet实例设置配置
        httpGet.setConfig(requestConfig);
        try {
            // 执行GET请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(response, httpClient);
        }
        return result;
    }

    /**
     * POST请求
     * @param httpUrl  请求地址
     * @param paramMap 请求参数
     * @return
     */
    public static String doPost(String httpUrl, Map<String, Object> paramMap) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = null;
        // 创建HttpClient实例
        httpClient = HttpClients.createDefault();
        // 创建HttpPost远程连接实例
        HttpPost httpPost = new HttpPost(httpUrl);
        // 设置配置请求参数：连接主机服务超时时间、请求超时时间和数据读取超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(60000)
                .build();
        // 为HttpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        // 封装POST请求参数
        if (MapUtils.isNotEmpty(paramMap)) {
            final List<NameValuePair> nvps = new ArrayList<>();
            paramMap.forEach((k, v) -> nvps.add(new BasicNameValuePair(k, v.toString())));
            // 为HttpPost设置封装好的请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
        }
        try {
            // HttpClient对象执行POST请求，并返回响应参数对象
            response = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(response, httpClient);
        }
        return result;
    }
}
