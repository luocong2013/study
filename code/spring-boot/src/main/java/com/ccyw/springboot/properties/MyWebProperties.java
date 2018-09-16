package com.ccyw.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author luoc
 * @version V1.0.0
 * @description 读取自定义配置文件中属性
 * @date 2018/6/28 22:18
 */
@ConfigurationProperties(prefix = "web")
@PropertySource("classpath:config/web.properties")
@Component
public class MyWebProperties {
    private String author;
    private String version;
    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
