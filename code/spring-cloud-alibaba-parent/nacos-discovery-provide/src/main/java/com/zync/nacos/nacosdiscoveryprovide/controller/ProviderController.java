package com.zync.nacos.nacosdiscoveryprovide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description 生产者控制器
 * @date 2020/11/18 21:15
 */
@RefreshScope
@RestController
public class ProviderController {

    @Autowired
    private Environment environment;

    @Value("${coder.username}")
    private String username;

    @GetMapping("/username")
    public String username() {
        return username;
    }

    @GetMapping("/username1")
    public String username1() {
        return environment.getProperty("coder.username");
    }

    @GetMapping("/hi/{username}")
    public String hi(@PathVariable String username) {
        return "hi " + username;
    }
}
