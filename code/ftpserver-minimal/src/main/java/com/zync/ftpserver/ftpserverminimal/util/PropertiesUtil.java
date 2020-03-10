package com.zync.ftpserver.ftpserverminimal.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 读取配置文件信息工具类
 * @date 2019/5/25 22:09
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertiesUtil {
    /**
     * 默认加载的配置文件
     */
    private static final String DEFAULT_PROPERTYFILE = "ftpserver/ftpserver.properties";

    private static Map<String, Properties> configMap = new HashMap<>(16);

    /**
     * 获取默认配置文件的属性值
     *
     * @param propertyName 属性key
     * @return 属性value
     */
    public static String get(String propertyName) {
        return getPropertyByFile(DEFAULT_PROPERTYFILE, propertyName);
    }

    /**
     * 获取属性内容
     *
     * @param configFile   properties配置文件
     * @param propertyName 属性key
     * @return 属性value
     */
    public static String getPropertyByFile(String configFile, String propertyName) {
        if (!configMap.containsKey(configFile)) {
            initConfig(configFile);
        }
        Properties prop = configMap.get(configFile);
        return prop.getProperty(propertyName);
    }

    /**
     * 载入配置文件，初始化后加入map
     *
     * @param configFile properties配置文件
     */
    private static void initConfig(String configFile) {
        try (InputStream in = getInputStream(configFile); InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            Properties prop = new Properties();
            prop.load(isr);
            configMap.put(configFile, prop);
        } catch (Exception e) {
            log.error("读取classes或者jar包同级目录下的配置文件 [{}] 出错.", configFile, e);
        }
    }

    /**
     * 读取配置文件
     *
     * @param configFile properties配置文件
     * @return 文件流
     * @throws Exception
     */
    private static InputStream getInputStream(String configFile) throws Exception {
        // 读取classes或者jar包同级目录下的配置文件
        // 先读取外面再读里面
        InputStream in;
        String path = System.getProperty("user.dir");
        path = URLDecoder.decode(path, StandardCharsets.UTF_8.name());
        File file = FileUtils.getFile(path, configFile);
        if (file.exists() && file.canRead()) {
            in = new FileInputStream(file);
        } else {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream(configFile);
        }
        return in;
    }
}
