package com.zync.websocket.primitive;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java原生WebSocket实现方式
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/17 11:16
 **/
@Slf4j
@Component
@ServerEndpoint("/ws/primitive/{sessionId}")
public class JavaPrimitiveWebSocket {

    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    private static final ConcurrentHashMap<String, Session> SESSION_POOL = new ConcurrentHashMap<>();

    private Session session;

    private String sessionId;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        // this.sessionId = session.getId();
        this.sessionId = session.getPathParameters().get("sessionId");
        SESSION_POOL.put(sessionId, session);
        int count = ONLINE_COUNT.incrementAndGet();
        log.info("WebSocket 新连接，sessionId: {}, 当前在线人数：{}", sessionId, count);
        sendMessage("连接成功！您的 SessionID: " + sessionId);
    }

    @OnClose
    public void onClose() {
        if (session != null) {
            SESSION_POOL.remove(sessionId);
            int count = ONLINE_COUNT.decrementAndGet();
            log.info("WebSocket 连接关闭，sessionId: {}, 当前在线人数：{}", sessionId, count);
        }
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("收到消息，sessionId: {}, 消息内容：{}", sessionId, message);
        sendMessage("用户 [" + sessionId + "] 发送消息：" + message);
    }

    @OnError
    public void onError(Throwable error) {
        log.error("WebSocket 发生错误，sessionId: {}", sessionId, error);
    }

    /**
     * 向当前 WebSocket 会话发送消息
     *
     * @param message 要发送的消息内容
     */
    private void sendMessage(String message) {
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("发送消息失败", e);
            }
        }
    }

    /**
     * 广播消息
     *
     * @param message 消息内容
     */
    private void broadcast(String message) {
        SESSION_POOL.forEach((id, session) -> {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    log.error("广播消息失败，sessionId: {}", id, e);
                }
            }
        });
    }

    /**
     * 广播消息
     *
     * @param message 消息内容
     */
    public static void broadcastToAll(String message) {
        SESSION_POOL.forEach((id, session) -> {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    log.error("广播消息失败，sessionId: {}", id, e);
                }
            }
        });
    }

    public static int getOnlineCount() {
        return ONLINE_COUNT.get();
    }

    public static ConcurrentHashMap<String, Session> getSessionPool() {
        return SESSION_POOL;
    }

}
