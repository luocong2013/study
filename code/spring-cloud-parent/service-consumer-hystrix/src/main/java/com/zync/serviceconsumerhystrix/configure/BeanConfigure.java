package com.zync.serviceconsumerhystrix.configure;

import feign.Feign;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption bean配置
 * @date 2019/4/21 21:13
 */
@SpringBootConfiguration
public class BeanConfigure {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Feign局部关闭Hystrix支持
     * 只需配置该bean
     * @return
     */
    //@Bean
    //@Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

}
