package com.zync.kafka.producter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luocong
 * @description 发送消息控制器
 * @date 2020/5/27 13:45
 */
@RestController
@RequestMapping("/kafka")
public class SendController {

    @Autowired
    private Producter producter;

    @RequestMapping(value = "/send")
    public String send() {
        producter.send();
        return "{\"code\":0}";
    }
}