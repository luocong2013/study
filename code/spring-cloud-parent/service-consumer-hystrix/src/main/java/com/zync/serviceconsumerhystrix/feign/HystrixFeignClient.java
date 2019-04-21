package com.zync.serviceconsumerhystrix.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 1. 基于Feign的熔断
 * @date 2019/4/21 21:16
 */
@FeignClient(name = "service-provider", fallback = HystrixFeignClientFallback.class)
public interface HystrixFeignClient {

    /**
     * hello方法
     * @param name
     * @return
     */
    @GetMapping("/provider/hello/{name}")
    String hello(@PathVariable("name") String name);

}
