package com.zync.serviceconsumerhystrix.feign.factory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 采用FallbackFactory实现熔断
 * @date 2019/4/21 22:18
 */
@FeignClient(name = "service-provider", fallbackFactory = HystrixFeignClientFallbackFactory.class)
public interface HystrixFeignClient {

    /**
     * hello方法
     * @param name
     * @return
     */
    @GetMapping("/provider/hello/{name}")
    String hello(@PathVariable("name") String name);

}
