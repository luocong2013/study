package com.ccyw.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luoc
 */
@SpringBootApplication
//@PropertySources({@PropertySource("classpath:config/web.properties")})//自定义配置文件（这里也可以这样定义，也可以在如MyWebProperties中那样定义）
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
