package com.mountain.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/11/29 15:47
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.mountain")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
