package com.zync.java;

import com.zync.common.StringReadUtil;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Java原生的HttpURLConnection
 * @date 2019/6/16 11:16
 */
public final class HttpClient {
    /**
     * GET请求方式
     */
    private final static String METHOD_GET = "GET";
    /**
     * POST请求方式
     */
    private final static String METHOD_POST = "POST";
    /**
     * 请求成功状态码
     */
    private static final int SC_OK = 200;

    private HttpClient() {
    }

    /**
     * GET请求
     * @param httpUrl 请求地址
     * @return
     */
    public static String doGet(String httpUrl) {
        HttpURLConnection connection = null;
        // 返回结果
        String result = null;
        try {
            // 创建远程url连接对象
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开一个连接，强转为HttpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式为 GET
            connection.setRequestMethod(METHOD_GET);
            // 设置连接主机服务器的超时时间为 15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间为 60000毫秒
            connection.setReadTimeout(60000);
            // 发生请求
            connection.connect();
            // 读取远程数据
            result = buildResponseData(connection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                // 关闭远程连接
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * POST请求
     * @param httpUrl 请求地址
     * @param param   请求参数
     * @return
     */
    public static String doPost(String httpUrl, String param) {
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        // 返回结果
        String result = null;
        try {
            // 创建远程url连接对象
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开一个连接，强转为HttpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式为 POST
            connection.setRequestMethod(METHOD_POST);
            // 设置连接主机服务器的超时时间为 15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间为 60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为 false，当向远程服务器传送数据/写数据时，需要设置为 true
            connection.setDoOutput(true);
            // 默认值为 true，当向远程服务读取数据时，设置为 true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式：请求参数应该是 name1=value1&name2=value2 的形式
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            outputStream = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去，它是通过字节数组写出去的
            outputStream.write(param.getBytes());
            // 读取远程数据
            result = buildResponseData(connection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
            if (connection != null) {
                // 关闭远程连接
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * 读取远程数据
     * @param connection http连接
     * @return
     */
    private static String buildResponseData(HttpURLConnection connection) {
        String result = null;
        InputStream inputStream = null;
        try {
            if (connection.getResponseCode() == SC_OK) {
                // 通过连接对象获取一个输入流，向远程读取
                inputStream = connection.getInputStream();
                // 从InputStream中读取字符串
                result = StringReadUtil.readStringByInputStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return result;
    }
}
