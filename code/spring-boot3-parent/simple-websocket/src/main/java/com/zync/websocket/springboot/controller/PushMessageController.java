package com.zync.websocket.springboot.controller;

import com.zync.websocket.springboot.utils.WsUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 推送消息接口
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/17 16:38
 **/
@RestController
@RequestMapping("/push")
public class PushMessageController {

    @PostMapping("/message/{userId}")
    public ResponseEntity<String> pushMessage(@PathVariable("userId") String userId, @RequestBody String content) {
        WsUtil.pushMessage(1, userId, content);
        return ResponseEntity.ok("推送成功");
    }

}
