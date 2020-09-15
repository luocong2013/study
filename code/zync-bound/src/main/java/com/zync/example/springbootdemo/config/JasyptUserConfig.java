package com.zync.example.springbootdemo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 01396453(luocong)
 * @version 3.0.3
 * @description jasypt加密的user配置信息
 * @date 2020/9/15 11:23
 */
@Getter
@Setter
@SpringBootConfiguration
@ConfigurationProperties(prefix = "user")
public class JasyptUserConfig {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String telphone;
}
