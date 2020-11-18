package com.zync.nacos.nacosdiscoveryconsumer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description 消费者控制器 RestTemplate方式
 * @date 2020/11/18 23:36
 */
@RestController
public class ConsumerRestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/chi/{username}")
    public String chi(@PathVariable String username) {
        return restTemplate.getForObject("http://nacos-discovery-provide/hi/" + username, String.class);
    }
}