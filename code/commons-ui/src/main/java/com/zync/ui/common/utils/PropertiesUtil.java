package com.zync.ui.common.utils;

import com.zync.ui.common.consts.Const;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public final class PropertiesUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 默认加载的配置文件
     */
    private static final String DEFAULT_PROPERTYFILE = "commons-ui-config.properties";

    private static Map<String, Properties> configMap = new HashMap<>(16);

    private PropertiesUtil() {
    }

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
            LOGGER.error("读取classes或者jar包同级目录下的【" + configFile + "】配置文件出错", e);
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
        InputStream in = null;
        String path = PropertiesUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        path = URLDecoder.decode(path, Const.ENCOD_UTF_8);
        File parent = FileUtils.getFile(path).getParentFile();
        File file = FileUtils.getFile(parent, configFile);
        if (file.exists() && file.canRead()) {
            in = new FileInputStream(file);
        } else {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream(configFile);
        }
        return in;
    }
}
