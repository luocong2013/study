package com.ysyue.im.controller;

import com.ysyue.im.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * IM 系统 REST API 控制器
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/23 9:52
 **/
@Slf4j
@RestController
@RequestMapping("/api/im")
@RequiredArgsConstructor
public class ImController {

    private final SessionManager sessionManager;

    /**
     * 获取系统状态
     */
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("serverStatus", "running");
        status.put("onlineUsers", sessionManager.getOnlineCount());
        status.put("timestamp", System.currentTimeMillis());
        return status;
    }

    /**
     * 检查用户是否在线
     */
    @GetMapping("/user/{userId}/online")
    public Map<String, Object> checkUserOnline(@PathVariable String userId) {
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("online", sessionManager.isOnline(userId));
        return result;
    }
}
