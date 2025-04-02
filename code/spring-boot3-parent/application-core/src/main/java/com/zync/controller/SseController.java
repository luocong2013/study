package com.zync.controller;

import com.zync.pojo.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 * @version 1.0
 * @description SSE
 * @since 2025/4/1 19:44
 **/
@Slf4j
@RestController
@RequestMapping("/sse")
public class SseController {

    // 存储所有连接的SSE Emitter（线程安全）
    private static final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    /**
     * 创建SSE连接
     */
    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter createConnection(@RequestParam String userId) {
        SseEmitter emitter = new SseEmitter(0L); // 0表示不超时

        // 事件回调处理
        emitter.onCompletion(() -> {
            log.info("连接完成：{}", userId);
            sseEmitters.remove(userId);
        });
        emitter.onTimeout(() -> {
            log.info("连接超时：{}", userId);
            sseEmitters.remove(userId);
        });
        emitter.onError(e -> {
            log.error("连接异常：{}", userId, e);
            sseEmitters.remove(userId);
        });

        // 发送初始消息
        try {
            emitter.send(SseEmitter.event().id("init").name("CONNECTED").data("连接已建立 - " + LocalDateTime.now()));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        sseEmitters.put(userId, emitter);
        return emitter;
    }

    /**
     * 向指定用户推送消息
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String userId, @RequestBody CustomMessage message) {
        SseEmitter emitter = sseEmitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().id(UUID.randomUUID().toString()).name("CUSTOM_MSG").data(message, MediaType.APPLICATION_JSON));
                return ResponseEntity.ok("消息已发送");
            } catch (IOException e) {
                return ResponseEntity.status(500).body("发送失败");
            }
        }
        return ResponseEntity.status(404).body("用户未连接");
    }

    /**
     * 主动关闭指定连接（管理接口）
     */
    @DeleteMapping("/close")
    public ResponseEntity<String> closeSse(@RequestParam String userId) {
        ResponseBodyEmitter emitter = sseEmitters.get(userId);
        if (emitter != null) {
            emitter.complete();
            sseEmitters.remove(userId);
            return ResponseEntity.ok("连接已关闭");
        }
        return ResponseEntity.status(404).body("连接不存在");
    }

}
