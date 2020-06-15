package com.zync.swx.socket;

import cn.hutool.http.HtmlUtil;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.socket
 * @description TODO
 * @date 2017-12-13 13:53
 */
public class UrlDemo {
    public static void main(String[] args) {
        InputStream in = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            String spec = "http://localhost:8081/solr";
            //创建一个URL对象
            URL url = new URL(spec);
            //通过URL的
            in = url.openStream();
            //将字节输入流转换为字符输入流
            isr = new InputStreamReader(in, "UTF-8");
            //为字符输入流添加缓冲
            br = new BufferedReader(isr);
            StringBuilder builder = new StringBuilder();
            String data = null;
            while ((data = br.readLine()) != null) {
                builder.append(data+"\n");
            }
            System.out.println(builder.toString());
            System.out.println(HtmlUtil.cleanHtmlTag(builder.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in, isr, br);
        }
    }
}
