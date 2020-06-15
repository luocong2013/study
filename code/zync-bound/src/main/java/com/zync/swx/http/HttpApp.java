package com.zync.swx.http;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author LC
 * @version V1.0.0
 * @date 2018-1-22 9:16
 */
public class HttpApp {

    public static void main(String[] args) throws Exception{
        int port = 8001;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/test", new TestHandler());
        //设置线程池，处理并发请求
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(4, 8, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(10000), factory, new ThreadPoolExecutor.AbortPolicy());
        server.setExecutor(executorService);
        server.start();
        System.out.printf("Http服务端口 %d 启动成功！\n", port);
    }

    static class TestHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "hello world";
            //获取查询字符串(get)
            String queryString = httpExchange.getRequestURI().getQuery();
            Map<String, String> queryStringInfo = formData2Dic(queryString);
            queryStringInfo.forEach((k, v) -> System.out.println("获取数据：" + k + "=>" + v));

            //获取表单提交数据(post)
            String postString = IOUtils.toString(httpExchange.getRequestBody());
            Map<String, String> postInfo = formData2Dic(postString);
            postInfo.forEach((k, v) -> System.out.println("获取数据：" + k + "=>" + v));


            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static Map<String, String> formData2Dic(String formData){
        Map<String, String> result = new HashMap<>(16);
        if (StringUtils.isBlank(formData)) {
            return result;
        }
        final String[] items = StringUtils.split(formData, "&");
        Arrays.stream(items).forEach(item -> {
            final String[] keyAndVal = StringUtils.split(item, "=");
            if (keyAndVal.length == 2) {
                try {
                    final String key = URLDecoder.decode(keyAndVal[0], "UTF-8");
                    final String val = URLDecoder.decode(keyAndVal[1], "UTF-8");
                    result.put(key, val);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        return result;
    }
}
