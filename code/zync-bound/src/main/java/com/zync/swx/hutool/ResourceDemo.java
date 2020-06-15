package com.zync.swx.hutool;


import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.ResourceUtil;

import java.io.*;
import java.net.URL;

/**
 * @author LC
 * @version V1.0.0
 * @date 2018-1-23 17:10
 */
public class ResourceDemo {
    public static void main(String[] args) throws Exception {
        /*URL url = ResourceDemo.class.getResource("/起诉意见书训练集/1.txt");
        if (url == null) {
            System.out.println(false);
        }
        File file = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
        System.out.println(file.exists());*/

        System.out.println("user.dir " + System.getProperty("user.dir"));
        System.out.println(ResourceDemo.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        System.out.println("\"\" 目录：" + ResourceDemo.class.getResource("").getPath());
        System.out.println("/起诉意见书训练集1 目录：" + ResourceDemo.class.getResource("/起诉意见书训练集/1.txt"));
        System.out.println("/起诉意见书训练集2 目录：" + ResourceDemo.class.getClassLoader().getResource("起诉意见书训练集/1.txt").getPath());

        System.out.println("/起诉意见书训练集3 目录：" + ResourceUtil.getResource("起诉意见书训练集/1.txt").getPath());
        System.out.println("/起诉意见书训练集4 目录：" + ResourceUtil.getResource("/起诉意见书训练集/1.txt", ResourceDemo.class).getPath());
        System.out.println("/起诉意见书训练集5 目录：" + ResourceUtil.getResourceObj("起诉意见书训练集/1.txt").getUrl().getPath());

        System.out.println(ResourceUtil.readUtf8Str("起诉意见书训练集/1.txt"));

        //URL url1 = ResourceDemo.class.getResource("/起诉意见书训练集/1.txt");
        URL url1 = ResourceDemo.class.getClassLoader().getResource("起诉意见书训练集/1.txt");
        //URL url1 =  ClassLoader.getSystemResource("起诉意见书训练集/1.txt");
        InputStream inputStream = url1.openStream();
        BufferedReader b = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String l = null;
        while ((l = b.readLine()) != null) {
            System.out.println(l);
        }

        ClassPathResource classPathResource = new ClassPathResource("起诉意见书训练集/1.txt");
        URL url = classPathResource.getUrl();
        File file = new File(url.toURI());

        FileReader reader1 = new FileReader(file);

        BufferedReader reader = new BufferedReader(reader1);

        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        System.out.println(url);
        System.out.println(classPathResource.getAbsolutePath());
    }
}
