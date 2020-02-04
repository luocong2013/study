package com.zync.httpclient3;

import com.zync.common.StringReadUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 使用HttpClient3.1实现HTTP请求
 * @date 2019/6/16 12:07
 */
public final class HttpClient3 {

    private HttpClient3() {
    }

    /**
     * GET方法
     * @param httpUrl 请求地址
     * @return
     */
    public static String doGet(String httpUrl) {
        // 输入流
        InputStream inputStream = null;
        // 返回结果
        String result = null;
        // 创建HttpClient实例
        HttpClient httpClient = new HttpClient();
        // 先获取连接管理器对象，再获取参数对象，再进行参数的赋值
        HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
        // 设置http连接主机服务器的超时时间为 15000毫秒
        managerParams.setConnectionTimeout(15000);
        // 设置读数据超时时间(单位毫秒)
        managerParams.setSoTimeout(20000);
        // 创建一个GET方法实例对象
        GetMethod getMethod = new GetMethod(httpUrl);
        // 设置GET请求超时时间为 60000毫秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        // 设置请求重试机制，默认重试次数为 3次，参数设置为 true，重试机制可用，false 相反
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, true));
        try {
            // 执行GET方法
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                // 如果状态码返回的不是 SC_OK，说明失败了，打印错误信息
                System.err.println("Method faild: " + getMethod.getStatusLine());
            } else {
                // 通过getMethod实例，获取远程的一个输入流
                inputStream = getMethod.getResponseBodyAsStream();
                // 从InputStream中读取字符串
                result = StringReadUtil.readStringByInputStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            IOUtils.closeQuietly(inputStream);
            // 释放连接
            getMethod.releaseConnection();
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
        // 输入流
        InputStream inputStream = null;
        String result = null;
        // 创建HttpClient实例
        HttpClient httpClient = new HttpClient();
        // 先获取连接管理器对象，再获取参数对象，再进行参数的赋值
        HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
        // 设置http连接主机服务器的超时时间为 15000毫秒
        managerParams.setConnectionTimeout(15000);
        // 设置读数据超时时间(单位毫秒)
        managerParams.setSoTimeout(20000);
        // 创建POST请求
        PostMethod postMethod = new PostMethod(httpUrl);
        // 设置POST请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);

        NameValuePair[] nvp = null;
        // 判断参数map集合是否为空
        if (MapUtils.isNotEmpty(paramMap)) {
            // 创建健值参数对象数组，大小为参数的个数
            nvp = new NameValuePair[paramMap.size()];
            // 循环遍历参数集合
            Iterator<Map.Entry<String, Object>> iterator = paramMap.entrySet().iterator();
            int index = 0;
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                nvp[index] = new NameValuePair(entry.getKey(), new String(entry.getValue().toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
                index++;
            }
        }
        // 判断nvp数组是否为空
        if (nvp != null && ArrayUtils.isNotEmpty(nvp)) {
            // 将参数存放到requestBody对象中
            postMethod.setRequestBody(nvp);
        }
        // 执行POST方法
        try {
            int statusCode = httpClient.executeMethod(postMethod);
            // 判断是否成功
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method faild: " + postMethod.getStatusLine());
            } else {
                // 获取远程返回的数据
                inputStream = postMethod.getResponseBodyAsStream();
                // 从InputStream中读取字符串
                result = StringReadUtil.readStringByInputStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            IOUtils.closeQuietly(inputStream);
            // 释放连接
            postMethod.releaseConnection();
        }
        return result;
    }
}
