package com.zync.gatewayzuul.controller;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 测试网关控制器
 * @date 2019/6/1 17:46
 */
@RestController
@RequestMapping("/local")
public class ZullController {

    @GetMapping("/hi")
    public String hi() {
        System.out.println("hi！");
        return "hi！" + DateFormatUtils.ISO_DATETIME_FORMAT.format(System.currentTimeMillis());
    }
}
