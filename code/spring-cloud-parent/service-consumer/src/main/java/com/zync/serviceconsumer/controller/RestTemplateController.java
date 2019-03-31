package com.zync.serviceconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author luoc
 * @version V1.0.0
 * @description 使用RestTemplate调用服务提供者的接口
 * @date 2019/3/31 21:32
 */
@RestController
@RequestMapping("/rest")
public class RestTemplateController {

    /**
     * ② 使用RestTemplate调用服务提供者的接口
     */
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/eureka-instance")
    public String serviceUrl() {
        return restTemplate.getForObject("http://service-provider/provider/eureka-instance", String.class);
    }

    @GetMapping("/index")
    public String index() {
        return restTemplate.getForObject("http://service-provider/provider/index", String.class);
    }
}
