package com.zync.eurekaserver.configure;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author luoc
 * @version V1.0.0
 * @description Eureka Server默认开启了CsrfFilter，导致微服务不能注册成功，需要关闭Eureka Server的CsrfFilter
 * @date 2019/3/31 20:34
 */
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        super.configure(http);
    }
}
