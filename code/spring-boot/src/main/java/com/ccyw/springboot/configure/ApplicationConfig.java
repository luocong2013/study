package com.ccyw.springboot.configure;

import com.ccyw.springboot.model.ConfigModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author luoc
 * @version V1.0.0
 * @description 应用配置类
 * @date 2018/7/23 21:24
 */
@Configuration
@PropertySource("classpath:config/config.properties")
public class ApplicationConfig {

    @Autowired
    private Environment env;

    @Bean
    public ConfigModel configModel() {
        ConfigModel configModel = new ConfigModel();
        configModel.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        configModel.setUrl(env.getProperty("jdbc.url"));
        configModel.setUsername(env.getProperty("jdbc.username"));
        configModel.setPassword(env.getProperty("jdbc.password"));
        return configModel;
    }


}
