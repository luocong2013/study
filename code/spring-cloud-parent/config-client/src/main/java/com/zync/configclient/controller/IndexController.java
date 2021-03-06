package com.zync.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @description 测试配置中心
 * 1. @RefreshScope 开启更新功能
 * @date 2019/3/24 22:30
 */
@RestController
@RefreshScope
public class IndexController {

    @Value("${address}")
    private String address;

    @Value("${ipthon}")
    private String ipthon;

    @GetMapping("/index")
    public String index() {
        return "地址：" + address + "\n电话：" + ipthon;
    }
}
