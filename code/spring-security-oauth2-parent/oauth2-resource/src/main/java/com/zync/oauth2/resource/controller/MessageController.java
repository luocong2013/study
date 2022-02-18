package com.zync.oauth2.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息控制器
 *
 * @author luocong
 * @version v1.0
 * @date 2022/2/18 15:22
 */
@RestController
public class MessageController {

    @GetMapping("/messages")
    public String[] getMessages() {
        return new String[]{"Message 1", "Message 2", "Message 3"};
    }

}
