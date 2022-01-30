package com.zync.okhttp;

import com.alibaba.fastjson.JSON;
import com.zync.entity.UserDTO;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption okhttp3实现HTTP请求
 * @date 2019/6/16 16:21
 */
public final class HttpClientOk {

    /**
     * 构建OkHttpClient对象
     */
    private final static OkHttpClient CLIENT = new OkHttpClient.Builder()
            // 设置连接超时时间
            .connectTimeout(60, TimeUnit.SECONDS)
            // 设置读取超时时间
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    private HttpClientOk() {
    }

    /**
     * GET请求
     * @param httpUrl 请求地址
     * @return
     */
    public static String doGet(String httpUrl) {
        // 创建Request对象
        Request request = new Request.Builder()
                .url(httpUrl)
                // 默认为GET请求，可以不写
                .get()
                .header("aa", "bbb")
                .build();
        return doExecute(request, null);
    }

    /**
     * PUT请求
     * @param httpUrl 请求地址
     * @return
     */
    public static String doPut(String httpUrl) {
        // 请求参数
        UserDTO user = new UserDTO(1L, "zth");
        String body = JSON.toJSONString(user);
        // 创建RequestBody对象
        RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json; charset=utf-8"));
        // 创建Request对象
        Request request = new Request.Builder().url(httpUrl).put(requestBody).build();
        return doExecute(request, body);
    }

    /**
     * POST请求
     * @param httpUrl 请求地址
     * @return
     */
    public static String doPost(String httpUrl) {
        // 请求参数
        Map<String, Object> param = new HashMap<>(16);
        param.put("id", 123L);
        param.put("name", "zth");
        String body = JSON.toJSONString(param);
        // 创建RequestBody对象
        RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json; charset=utf-8"));
        // 创建Request对象
        Request request = new Request.Builder().url(httpUrl).post(requestBody).build();
        return doExecute(request, body);
    }

    /**
     * 上传文件
     * @param httpUrl
     * @return
     */
    public static String doUpload(String httpUrl) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "docker_practice.pdf", RequestBody.create(new File("~/Documents/pdf/docker_practice.pdf"), MediaType.parse("multipart/form-data")))
                .build();
        // 创建Request对象
        Request request = new Request.Builder().url(httpUrl).post(requestBody).build();
        return doExecute(request, null);
    }

    /**
     * DELETE请求
     * @param httpUrl
     * @return
     */
    public static String doDelete(String httpUrl) {
        // 创建Request对象
        Request request = new Request.Builder().url(httpUrl).delete().build();
        return doExecute(request, null);
    }

    /**
     * 请求的取消
     * @param httpUrl
     * @return
     * @throws IOException
     */
    public static String doCancelSysnc(String httpUrl) throws IOException {
        String result = null;
        // 创建Request对象
        Request request = new Request.Builder().url(httpUrl).get().build();
        final Call call = CLIENT.newCall(request);
        Response response = call.execute();
        long start = System.currentTimeMillis();

        // 测试连接的取消
        while (true) {
            // 1秒钟获取不到结果就取消请求
            if (System.currentTimeMillis() - start > 1000) {
                call.cancel();
                System.out.println("task canceled.");
                break;
            }
        }
        ResponseBody body = response.body();
        if (body != null) {
            result = body.string();
        }
        return result;
    }

    /**
     * 执行请求
     * @param request
     * @return
     */
    private static String doExecute(Request request, String body) {
        String result = null;
        try {
            long start = System.currentTimeMillis();
            requestLog(request, body, null);
            // 使用OkHttpClient对象调用newCall()方法来创建Call对象，调用execute()方法发送请求并获取服务器返回的数据
            Response response = CLIENT.newCall(request).execute();
            // 通过response.body()获取响应体，进而再调用其他方法获取返回的具体内容
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                result = responseBody.string();
                responseLog(response, response.code(), result, start);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 请求开始日志
     * @param request
     * @param body
     * @param formParam
     */
    private static void requestLog(Request request, String body, String formParam) {
        System.out.println("===========================request begin================================================");
        System.out.printf("URI          : %s%n", request.url());
        System.out.printf("Method       : %s%n", request.method());
        System.out.printf("Headers      : %s%n", header(request.headers()));
        if (StringUtils.isNotBlank(body)) {
            System.out.printf("Request body : %s%n", body);
        }
        if (StringUtils.isNotBlank(formParam)) {
            System.out.printf("Request param: %s%n", formParam);
        }
        System.out.println("---------------------------request end--------------------------------------------------");
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
        System.out.printf("Status       : %s%n", httpCode);
        System.out.printf("Headers      : %s%n", header(response.headers()));
        System.out.printf("Response     : %s%n", result);
        System.out.printf("Time         : %s%n ms", endTime - startTime);
        System.out.println("===========================response end================================================");
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
}
