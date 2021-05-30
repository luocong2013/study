package com.zync.nacos.application1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 启动类
 * @date 2021/5/30 17:53
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application1Bootstrap {

    public static void main(String[] args) {
        SpringApplication.run(Application1Bootstrap.class, args);
    }
}
