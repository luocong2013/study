package com.zync.serviceprovider.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @description 服务提供者
 * @date 2019/3/19 22:10
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/eureka-instance")
    public String serviceUrl() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("SERVICE-PROVIDER", false);
        return instance.getHomePageUrl();
    }

    @GetMapping("/index")
    public String index() {
        return "这里是Service Provider";
    }
}
