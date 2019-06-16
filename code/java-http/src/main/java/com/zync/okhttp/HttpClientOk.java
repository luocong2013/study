package com.zync.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption okhttp3实现HTTP请求
 * @date 2019/6/16 16:21
 */
public final class HttpClientOk {

    private HttpClientOk() {
    }

    /**
     * GET请求
     * @param httpUrl 请求地址
     * @return
     */
    public static String doGet(String httpUrl) {
        String result = null;
        // 创建Request对象
        Request request = new Request.Builder().url(httpUrl).build();
        // 创建OkHttpClient对象
        OkHttpClient httpClient = new OkHttpClient();
        try {
            // 使用OkHttpClient对象调用newCall()方法来创建Call对象，调用execute()方法发送请求并获取服务器返回的数据
            Response response = httpClient.newCall(request).execute();
            // 通过response.body()获取响应体，进而再调用其他方法获取返回的具体内容
            ResponseBody body = response.body();
            if (body != null) {
                // 获取响应体字符串
                result = body.string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
