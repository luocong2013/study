package com.zync.serviceconsumerhystrix.controller;

import com.zync.serviceconsumerhystrix.feign.HystrixFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption FeignClient控制器
 * @date 2019/4/21 21:42
 */
@RestController
@RequestMapping("/feign")
public class FeignClientController {

    @Autowired
    private HystrixFeignClient hystrixFeignClient;

    @Autowired
    private com.zync.serviceconsumerhystrix.feign.factory.HystrixFeignClient hystrixFeignClientFactory;

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return hystrixFeignClient.hello(name);
    }

    @GetMapping("/helloFactory")
    public String helloFactory(@RequestParam("name") String name) {
        return hystrixFeignClientFactory.hello(name);
    }

}
