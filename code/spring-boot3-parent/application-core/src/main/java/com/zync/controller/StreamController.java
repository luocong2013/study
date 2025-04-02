package com.zync.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 * @version 1.0
 * @description Stream
 * @since 2025/4/1 17:36
 **/
@RestController
@RequestMapping("/stream")
public class StreamController {

    // 存储活动的Emitter对象（生产环境建议使用ConcurrentHashMap）
    private static final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>(16);

    @GetMapping(value = "/{clientId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter createStream(@PathVariable String clientId, @RequestParam(defaultValue = "30") long timeout) {

        SseEmitter emitter = new SseEmitter(timeout * 1000);

        // 事件回调处理
        emitter.onCompletion(() -> {
            emitters.remove(clientId);
            System.out.println("连接完成: " + clientId);
        });

        emitter.onTimeout(() -> {
            emitters.remove(clientId);
            System.out.println("连接超时: " + clientId);
        });

        emitter.onError(e -> {
            emitters.remove(clientId);
            System.err.println("连接异常: " + clientId + " - " + e.getMessage());
        });

        // 开启异步线程处理数据并发送
        new Thread(() -> generateDataStream(emitter, clientId)).start();

        emitters.put(clientId, emitter);
        return emitter;
    }

    /**
     * 主动关闭指定连接（管理接口）
     */
    @DeleteMapping("/{clientId}")
    public ResponseEntity<String> closeStream(@PathVariable String clientId) {
        SseEmitter emitter = emitters.get(clientId);
        if (emitter != null) {
            emitter.complete();
            return ResponseEntity.ok("连接已关闭");
        }
        return ResponseEntity.status(404).body("连接不存在");
    }

    /**
     * 模拟数据生成任务
     */
    private void generateDataStream(SseEmitter emitter, String clientId) {
        try {
            for (int i = 1; i <= 20; i++) {
                CustomData data = new CustomData(clientId, i, System.currentTimeMillis());
                // String jsonData = String.format("{\"clientId\":\"%s\",\"seq\":%d,\"timestamp\":%d}", clientId, i, System.currentTimeMillis());

                // 发送带元数据的事件
                emitter.send(SseEmitter.event().id(String.valueOf(i)).name("PROGRESS_UPDATE").data(data, MediaType.APPLICATION_JSON).reconnectTime(5000L));

                Thread.sleep(1000); // 模拟处理延迟
            }
            emitter.complete();
        } catch (Exception e) {
            // 出现异常时结束响应并传递错误信息
            emitter.completeWithError(e);
        }
    }

    private record CustomData(String clientId, int seq, long timestamp) {}


}
