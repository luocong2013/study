package com.zync.nacos.nacosdiscoveryconsumer.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description 消费者配置文件
 * @date 2020/11/18 23:22
 */
@SpringBootConfiguration
public class ConsumerConfig {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
