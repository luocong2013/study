package com.zync.nacos.service2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 生产者服务启动类
 * @date 2021/5/30 18:24
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Service2Bootstrap {

    public static void main(String[] args) {
        SpringApplication.run(Service2Bootstrap.class, args);
    }
}
