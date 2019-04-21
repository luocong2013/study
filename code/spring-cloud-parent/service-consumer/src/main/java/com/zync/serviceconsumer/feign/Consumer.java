package com.zync.serviceconsumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author luoc
 * @version V1.0.0
 * @description Consumer接口
 * @date 2019/3/19 22:29
 */
@FeignClient(name = "service-provider", fallback = ConsumerFallback.class)
public interface Consumer {

    /**
     * 服务地址
     * @return
     */
    @GetMapping(value = "/provider/eureka-instance")
    String serviceUrl();

    /**
     * 测试服务
     * @return
     */
    @GetMapping(value = "/provider/index")
    String index();

    /**
     * hello方法
     * @param name
     * @return
     */
    @GetMapping("/provider/hello/{name}")
    String hello(@PathVariable("name") String name);
}
