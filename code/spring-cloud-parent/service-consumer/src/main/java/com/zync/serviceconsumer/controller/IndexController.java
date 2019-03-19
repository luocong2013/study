package com.zync.serviceconsumer.controller;

import com.zync.serviceconsumer.feign.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @description 入口
 * @date 2019/3/19 22:34
 */
@RestController
public class IndexController {

    @Autowired
    private Consumer consumer;

    @GetMapping("/index")
    public String index() {
        return consumer.index();
    }
}
