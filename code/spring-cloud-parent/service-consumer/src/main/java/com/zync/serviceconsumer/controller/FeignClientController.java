package com.zync.serviceconsumer.controller;

import com.zync.serviceconsumer.feign.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @description 使用FeignClient调用服务提供者的接口
 * @date 2019/3/19 22:34
 */
@RestController
@RequestMapping("/feign")
public class FeignClientController {

    /**
     * ① 使用FeignClient调用服务提供者的接口
     */
    @Autowired
    private Consumer consumer;

    @GetMapping("/eureka-instance")
    public String serviceUrl() {
        return consumer.serviceUrl();
    }

    @GetMapping("/index")
    public String index() {
        return consumer.index();
    }

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return consumer.hello(name);
    }
}
