package com.zync.nacos.application1.controller;

import com.zync.nacos.service1.api.ConsumerService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 应用控制层
 * @date 2021/5/30 17:47
 */
@RestController
public class Application1Controller {

    @DubboReference
    private ConsumerService consumerService;

    @GetMapping("/service")
    public String service() {
        return "test " + consumerService.service();
    }
}
