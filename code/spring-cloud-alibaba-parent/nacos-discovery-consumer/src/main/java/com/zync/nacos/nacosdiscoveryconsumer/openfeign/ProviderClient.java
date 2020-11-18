package com.zync.nacos.nacosdiscoveryconsumer.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description 生产者客户端
 * @date 2020/11/19 0:36
 */
@FeignClient("nacos-discovery-provide")
public interface ProviderClient {

    @GetMapping("/hi/{username}")
    String hi(@PathVariable("username") String username);

}
