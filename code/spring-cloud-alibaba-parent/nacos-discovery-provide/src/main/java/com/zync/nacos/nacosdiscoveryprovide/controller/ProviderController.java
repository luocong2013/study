package com.zync.nacos.nacosdiscoveryprovide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description 生产者控制器
 * @date 2020/11/18 21:15
 */
@RestController
public class ProviderController {

    @GetMapping("/hi/{username}")
    public String hi(@PathVariable String username) {
        return "hi " + username;
    }
}
