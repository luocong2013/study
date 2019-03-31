package com.zync.serviceconsumer.configure;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author luoc
 * @version V1.0.0
 * @description Bean配置
 * @date 2019/3/31 21:09
 */
@SpringBootConfiguration
public class BeanConfigure {

    /**
     * 1. 使用RestTemplate调用服务提供者的接口
     * 2. 使用ribbon做客户端负载均衡@LoadBalanced（默认的负载均衡方式是轮询）
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
