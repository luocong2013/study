package com.zync.serviceconsumerhystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 2. Controller中实现熔断方法
 *          @HystrixCommand 指定融断方法 注意熔断器的方法一定和原方法的名称保持一致
 *          配置@HystrixCommand(fallbackMethod = "helloFallback", commandProperties = @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"))
 * @date 2019/4/21 21:29
 */
@RestController
@RequestMapping("/rest")
public class RestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    @HystrixCommand(fallbackMethod = "helloFallback")
    public String hello(@RequestParam("name") String name) {
        return this.restTemplate.getForObject("http://service-provider/provider/hello/" + name, String.class);
    }

    public String helloFallback(String name) {
        return "Feign客户端访问失败";
    }

}
