package com.zync;

import com.zync.java.HttpClient;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Http请求测试
 * @date 2019/6/16 11:38
 */
public class Client {

    public static void main(String[] args) {
        String httpUrl = "https://www.cuit.edu.cn/";
        String result;
        // 1.Java原生的HttpURLConnection
        // GET请求
        result = HttpClient.doGet(httpUrl);
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
        //result = HttpClientOk.doGet(httpUrl);

        // 打印请求结果
        System.out.println(result);
    }
}
