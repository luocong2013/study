package com.zync.security.session.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 应用配置文件，相对于application.xml文件
 * @date 2020/7/11 14:19
 */
@Configuration
@ComponentScan(basePackages = "com.zync.security.session",
                excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
public class ApplicationConfig {
    // 在此配置除了Controller的其他的bean，比如数据库连接池、事物管理器、业务bean等
}
