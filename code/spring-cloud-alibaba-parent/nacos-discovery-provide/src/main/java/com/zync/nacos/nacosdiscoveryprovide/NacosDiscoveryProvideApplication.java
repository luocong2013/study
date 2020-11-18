package com.zync.nacos.nacosdiscoveryprovide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luocong
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosDiscoveryProvideApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoveryProvideApplication.class, args);
    }

}
