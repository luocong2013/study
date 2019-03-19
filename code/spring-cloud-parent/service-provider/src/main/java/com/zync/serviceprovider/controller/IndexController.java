package com.zync.serviceprovider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @description 入口
 * @date 2019/3/19 22:10
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "这里是Service Provider";
    }
}
