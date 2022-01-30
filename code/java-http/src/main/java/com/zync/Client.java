package com.zync;

import com.zync.okhttp.HttpClientOk;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Http请求测试
 * @date 2019/6/16 11:38
 */
public class Client {

    public static void main(String[] args) {
        //String httpUrl = "http://www.sctv.com/";
        String httpUrl = "http://127.0.0.1:9999/logback/post/";
        String result;
        // 1.Java原生的HttpURLConnection
        // GET请求
        //result = HttpClient.doGet(httpUrl);
        // POST请求
        //result = HttpClient.doPost(httpUrl, "");

        // 2. HttpClient3.1
        // GET请求
        //result = HttpClient3.doGet(httpUrl);
        // POST请求
        //result = HttpClient3.doPost(httpUrl, null);

        // 3. HttpClient4.5
        // GET请求
        //result = HttpClient4.doGet(httpUrl);
        // POST请求
        //result = HttpClient4.doPost(httpUrl, null);

        // 4. OkHttp
        // GET请求
        result = HttpClientOk.doPost(httpUrl);

        // 5. HttpAsyncClient
        // GET请求
        //result = HttpAsyncClient.doGet(httpUrl);
        //result = HttpAsyncClient.doPost(httpUrl, null);

        // 打印请求结果
        System.out.println(result);
    }
}
