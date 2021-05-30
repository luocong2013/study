package com.zync.nacos.service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 消费者服务启动类
 * @date 2021/5/30 18:06
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Service1Bootstrap {

    public static void main(String[] args) {
        SpringApplication.run(Service1Bootstrap.class, args);
    }
}
