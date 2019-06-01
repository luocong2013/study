package com.zync.turbineserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author luoc
 * @descrption 监控集群下hystrix的metrics情况
 * 1. http://localhost:8720/turbine.stream 地址查看接口信息
 * 2. 结合Hystrix Dashboard查看信息
 *  1) 引入spring-cloud-starter-netflix-hystrix-dashboard依赖
 *  2) 启动类添加@EnableHystrixDashboard注解
 *  3) 访问Dashboard http://localhost:8720/hystrix
 *  4) 在Hystrix Dashboard中填入 http://localhost:8720/turbine.stream
 *  5) 点击 Monitor Stream 查看
 *
 * 3. EnableTurbine 启用Turbine
 *    EnableHystrixDashboard 启用Hystrix Dashboard监控
 *
 * 4. 监控
 * 1) 默认的集群监控：通过URL http://turbine-hostname:port/turine.stream开启，实现对默认集群的监控
 * 2) 指定的集群监控：通过URL http://turbine-hostname:port/turine.stream?cluster=[clusterName]开启，实现对clusterName集群的监控
 * 3) 单体应用的监控：通过URL http://hystrix-app:port/actuator/hystrix.stream开启，实现对具体某个服务实例的监控
 * @version 1.0.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableTurbine
@EnableHystrixDashboard
public class TurbineServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurbineServerApplication.class, args);
    }

}
