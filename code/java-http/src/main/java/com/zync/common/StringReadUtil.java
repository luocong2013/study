package com.zync.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 读取字符串工具类
 * @date 2019/6/16 14:31
 */
public final class StringReadUtil {

    private StringReadUtil() {
    }

    /**
     * 从InputStream中读取字符串
     * @param inputStream 输入流
     * @return
     */
    public static String readStringByInputStream(InputStream inputStream) {
        // 封装输入流inputStream，并指定字符集
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            // 存放数据
            StringBuilder builder = new StringBuilder();
            String temp;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp).append("\r\n");
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
