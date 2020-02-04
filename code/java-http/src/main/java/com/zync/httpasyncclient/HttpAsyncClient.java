package com.zync.httpasyncclient;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 异步http请求
 * @date 2020/2/4 15:22
 */
public final class HttpAsyncClient {

    private HttpAsyncClient() {
    }

    /**
     * 请求参数，可以设置连接超时时间、代理地址等等
     */
    private static RequestConfig requestConfig = null;
    /**
     * 连接管理器
     */
    private static PoolingNHttpClientConnectionManager connectionManager = null;
    /**
     * 异步连接客户端
     */
    private static volatile CloseableHttpAsyncClient asyncClient = null;

    static {
        // 代理地址
        //HttpHost proxy = new HttpHost("192.168.0.111", 3127);
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(6000)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(6000)
                //.setProxy(proxy)
                .build();

        IOReactorConfig reactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setSoKeepAlive(false)
                .build();
        try {
            ConnectingIOReactor connectingIOReactor = new DefaultConnectingIOReactor(reactorConfig);
            connectionManager = new PoolingNHttpClientConnectionManager(connectingIOReactor);
            // 连接池最大数
            connectionManager.setMaxTotal(20);
            // 每个路由基础连接数
            connectionManager.setDefaultMaxPerRoute(20);
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取异步连接客户端
     * @return
     */
    private static CloseableHttpAsyncClient getAsyncClient() {
        if (asyncClient == null) {
            synchronized (HttpAsyncClient.class) {
                if (asyncClient == null) {
                    asyncClient = HttpAsyncClients.custom()
                            .setConnectionManager(connectionManager)
                            .setDefaultRequestConfig(requestConfig)
                            .build();
                }
            }
        }
        return asyncClient;
    }

    /**
     * GET请求
     * @param httpUrl 请求地址
     * @return
     */
    public static String doGet(String httpUrl) {
        CloseableHttpAsyncClient client = getAsyncClient();
        client.start();
        HttpGet httpGet = new HttpGet(httpUrl);
        String result = null;
        try {
            HttpResponse response = client.execute(httpGet, null).get();
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(client);
        }
        return result;
    }

    /**
     * POST请求
     * @param httpUrl 请求地址
     * @param paramMap 请求参数
     * @return
     */
    public static String doPost(String httpUrl, Map<String, Object> paramMap) {
        CloseableHttpAsyncClient client = getAsyncClient();
        client.start();
        HttpPost httpPost = new HttpPost(httpUrl);
        String result = null;
        // 封装POST请求参数
        if (MapUtils.isNotEmpty(paramMap)) {
            final List<NameValuePair> nvps = new ArrayList<>();
            paramMap.forEach((k, v) -> nvps.add(new BasicNameValuePair(k, v.toString())));
            // 为HttpPost设置封装好的请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
        }
        try {
            HttpResponse response = client.execute(httpPost, null).get();
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(client);
        }
        return result;
    }


}
