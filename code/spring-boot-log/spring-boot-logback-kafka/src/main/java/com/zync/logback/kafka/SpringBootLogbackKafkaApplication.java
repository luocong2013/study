package com.zync.logback.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * logback kafka springboot启动类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/25 11:30
 */
@SpringBootApplication
public class SpringBootLogbackKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLogbackKafkaApplication.class, args);
    }
}
