package com.zync.security.springsecurity.init;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Spring Security初始化类
 * @date 2020/7/11 16:25
 */
public class SpringSecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SpringSecurityApplicationInitializer() {
        // 在非Spring环境中，需要这样配置Spring Security
        // 在Spring环境中，就在SpringApplicationInitializer里初始化
        //super(WebSecurityConfig.class);
    }
}
