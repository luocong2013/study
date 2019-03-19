package com.zync.serviceconsumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author luoc
 * @version V1.0.0
 * @description Consumer接口
 * @date 2019/3/19 22:29
 */
@FeignClient(name = "service-provide", fallback = ConsumerFallback.class)
public interface Consumer {

    /**
     * 入口
     * @return
     */
    @GetMapping(value = "/index")
    String index();
}
