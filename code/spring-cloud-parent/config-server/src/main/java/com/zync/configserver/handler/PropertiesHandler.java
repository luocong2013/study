package com.zync.configserver.handler;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author luoc
 * @version V1.0.0
 * @description 解决 spring cloud 配置有中文时乱码问题
 * spring 默认使用org.springframework.boot.env.PropertiesPropertySourceLoader来加载配置
 * 底层是通过调用Properties的load方法，而load方法输入流的编码是 ISO 8859-1
 * @date 2019/3/24 22:36
 */
public class PropertiesHandler implements PropertySourceLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesHandler.class);

    @Override
    public String[] getFileExtensions() {
        return new String[]{"properties", "xml"};
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        Properties properties = getProperties(resource);
        if (!properties.isEmpty()) {
            PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(name, properties);
            return Collections.singletonList(propertiesPropertySource);
        }
        return null;
    }

    private Properties getProperties(Resource resource) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            properties.load(new InputStreamReader(inputStream, "UTF-8"));
        } catch (IOException e) {
            LOGGER.error("load inputstream failure ...", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return properties;
    }
}
