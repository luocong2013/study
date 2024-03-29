package com.zync.chat.common.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读取配置文件信息工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 17:28
 */
@Slf4j
@UtilityClass
public class PropertiesUtil {

    /**
     * 默认加载的配置文件
     */
    private static final String DEFAULT_CONFIG_FILE = "application.properties";

    private static final Map<String, Properties> CONFIG_MAP = new ConcurrentHashMap<>(16);

    /**
     * 获取默认配置文件的属性值
     *
     * @param propertyName 属性key
     * @return 属性value
     */
    public static String get(String propertyName) {
        return getPropertyByFile(DEFAULT_CONFIG_FILE, propertyName);
    }

    /**
     * 获取属性内容
     *
     * @param configFile   properties配置文件
     * @param propertyName 属性key
     * @return 属性value
     */
    public static String getPropertyByFile(String configFile, String propertyName) {
        if (!CONFIG_MAP.containsKey(configFile)) {
            initConfig(configFile);
        }
        Properties prop = CONFIG_MAP.get(configFile);
        return prop.getProperty(propertyName);
    }


    /**
     * 载入配置文件，初始化后加入map
     *
     * @param configFile properties配置文件
     */
    private static void initConfig(String configFile) {
        try (InputStream in = getInputStream(configFile)) {
            Properties prop = new Properties();
            prop.load(in);
            CONFIG_MAP.put(configFile, prop);
        } catch (Exception e) {
            log.error("读取classes或者jar包同级目录下的【{}】配置文件出错", configFile, e);
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
        String path = PropertiesUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        path = URLDecoder.decode(path, StandardCharsets.UTF_8);
        File parent = new File(path).getParentFile();
        File file = new File(parent, configFile);
        if (file.exists() && file.canRead()) {
            in = new FileInputStream(file);
        } else {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream(configFile);
        }
        return in;
    }

}
