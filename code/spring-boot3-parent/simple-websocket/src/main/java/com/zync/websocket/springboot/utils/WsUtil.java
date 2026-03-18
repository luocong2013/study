package com.zync.websocket.springboot.utils;

import com.zync.websocket.springboot.holder.SpringContextHolder;
import com.zync.websocket.springboot.model.WsMessage;
import com.zync.websocket.springboot.service.WsMessageStoreService;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 推送web socket消息工具类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/17 16:16
 **/
@Slf4j
@UtilityClass
public class WsUtil {

    private static final ConcurrentHashMap<String, WebSocketSession> SESSION_MAP = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, WebSocketSession> getSessionMap() {
        return SESSION_MAP;
    }

    /**
     * 推送消息
     *
     * @param type 消息类型
     * @param userId 用户ID
     * @param content 消息内容
     */
    public void pushMessage(Integer type, String userId, String content) {
        // 创建消息
        WsMessage message = new WsMessage();
        message.setId(System.currentTimeMillis());
        message.setType(type);
        message.setUserId(userId);
        message.setContent(content);
        message.setStatus(0);
        // 存储消息
        SpringContextHolder.getBean(WsMessageStoreService.class).save(message);

        // 获取用户会话
        WebSocketSession session = SESSION_MAP.get(userId);
        if (session != null) {
            // 异步推送消息
            CompletableFuture.runAsync(() -> pushMessage(session, message));
        }
    }

    /**
     * 推送消息
     *
     * @param session WebSocketSession
     * @param message 要推送的消息对象
     */
    public void pushMessage(WebSocketSession session, WsMessage message) {
        retryPush(session, message, 0);
    }

    /**
     * 重试推送消息（最多3次）
     *
     * @param session WebSocket 会话
     * @param message 要推送的消息对象
     * @param retryCount 当前重试次数
     */
    private void retryPush(WebSocketSession session, WsMessage message, int retryCount) {
        if (retryCount > 3) {
            // 重试次数超过3次，不再重试，存库
            SpringContextHolder.getBean(WsMessageStoreService.class).updateMessageStatus(message.getId(), 2);
            return;
        }
        try {
            if (session.isOpen()) {
                // 发送消息格式：（消息格式：messageId|messageContent）
                // String sendContent = message.getId() + "|" + message.getContent();
                // 推送消息
                session.sendMessage(new TextMessage(JacksonUtil.toJson(message)));
                // 等待确认（这里简化处理，实际可添加超时重试机制）
                TimeUnit.SECONDS.sleep(1);
                // 检查消息状态，如果还是未推送，说明未确认，重试（最多3次）
                WsMessage dbMessage = SpringContextHolder.getBean(WsMessageStoreService.class).findById(message.getId());
                if (!dbMessage.isPushed()) {
                    // 重试推送消息
                    retryPush(session, message, retryCount + 1);
                }
            }
        } catch (Exception e) {
            retryPush(session, message, retryCount + 1);
            log.error("重试推送消息失败，sessionId: {}", session.getId(), e);
        }
    }

}
