package com.zync.serviceconsumer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption FeignClient使用Configuration
 * @date 2019/4/21 18:36
 */
@RestController
@RequestMapping("/feign2")
public class FeignClient2Controller {

    /**
     * 使用Feign原生注解与使用SpringMVC的注解只能二选一
     */
    //@Autowired
    //private Consumer2 consumer2;
    //
    //@GetMapping("/hello")
    //public String hello(@RequestParam("name") String name) {
    //    return consumer2.hello(name);
    //}
}
