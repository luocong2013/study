package com.zync.serviceconsumerhystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @descrption 服务消费者 - 熔断机制
 *              EnableHystrixDashboard 应用入口指定对HystrixDashboard的支持，访问地址：http://localhost:8742/hystrix/
 * @version V1.0.0
 * @author luoc
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrixDashboard
public class ServiceConsumerHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerHystrixApplication.class, args);
    }

}
