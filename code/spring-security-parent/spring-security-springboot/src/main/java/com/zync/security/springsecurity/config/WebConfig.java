package com.zync.security.springsecurity.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption servlet context配置
 * @date 2020/7/18 15:00
 */
@SpringBootConfiguration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 默认url根路径跳转到/login，此url为spring security提供
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("redirect:/login");

        // 自定义登录页面
        registry.addViewController("/").setViewName("redirect:/login-view");
        registry.addViewController("/login-view").setViewName("login");
    }
}
