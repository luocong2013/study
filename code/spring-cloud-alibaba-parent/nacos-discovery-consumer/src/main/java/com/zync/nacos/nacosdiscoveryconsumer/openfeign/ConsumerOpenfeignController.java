package com.zync.nacos.nacosdiscoveryconsumer.openfeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description 消费者控制器 OpenFeign方式
 * @date 2020/11/19 0:24
 */
@RestController
public class ConsumerOpenfeignController {

    @Autowired
    private ProviderClient providerClient;

    @GetMapping("/fhi/{username}")
    public String fhi(@PathVariable String username) {
        return providerClient.hi(username);
    }
}
