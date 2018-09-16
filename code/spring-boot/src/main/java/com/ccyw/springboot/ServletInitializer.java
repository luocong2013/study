package com.ccyw.springboot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author luoc
 * @version V1.0.0
 * @description 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 * @date 2018/7/11 22:45
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 这里一定要指向原先用main方法执行的Application启动类
        return builder.sources(Application.class);
    }
}
