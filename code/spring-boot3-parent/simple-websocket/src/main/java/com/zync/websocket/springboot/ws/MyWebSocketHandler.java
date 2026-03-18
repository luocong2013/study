package com.zync.websocket.springboot.ws;

import com.zync.websocket.springboot.holder.SpringContextHolder;
import com.zync.websocket.springboot.model.WsMessage;
import com.zync.websocket.springboot.service.WsMessageStoreService;
import com.zync.websocket.springboot.utils.JacksonUtil;
import com.zync.websocket.springboot.utils.WsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Objects;

/**
 * WebSocket处理器，处理连接、断开、消息推送、消息确认
 * 这是我实现的处理器, 也是 WebSocket 开发的核心:
 * 需要实现 WebSocketHandler 接口, 该接口提供了五个方法。
 * 1、afterConnectionEstablished(): 建立新的 socket 连接后回调的方法。
 * 2、handleMessage(): 接收客户端发送的 Socket。
 * 3、handleTransportError(): 连接出错时，回调的方法。
 * 4、afterConnectionClosed(): 连接关闭时，回调的方法。
 * 5、supportsPartialMessages(): 这个是 WebSocketHandler 是否处理部分消息，没什么卵用 返回 false 就完事了。
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/17 14:52
 **/
@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    /**
     * 建立新的 socket 链接后回调的方法
     *
     * @param session WebSocketSession
     * @throws Exception 抛出异常
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 从路径中获取userId（对应前端连接路径 /ws/{userId}）
        String userId = (String) session.getAttributes().get("userId");
        log.info("用户 {} 上线", userId);
        if (StringUtils.isNotBlank(userId)) {
            // 用户信息存入缓存
            WsUtil.getSessionMap().put(userId, session);
            // 关键：用户上线后，查询未推送和推送失败消息，并推送给用户
            List<WsMessage> unPushMessages = SpringContextHolder.getBean(WsMessageStoreService.class).findUnPushByUserId(userId);
            if (!CollectionUtils.isEmpty(unPushMessages)) {
                // 补推消息
                for (WsMessage message : unPushMessages) {
                    WsUtil.pushMessage(session, message);
                }
            }
            WsUtil.pushMessage(2, userId, "欢迎用户 " + userId + " 上线");
        }
    }

    /**
     * 连接断开时：从缓存中移除用户会话
     *
     * @param session WebSocketSession
     * @param status 关闭状态
     * @throws Exception 抛出异常
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 从路径中获取userId（对应前端连接路径 /ws/{userId}）
        String userId = (String) session.getAttributes().get("userId");
        log.info("用户 {} 下线", userId);
        if (StringUtils.isNotBlank(userId)) {
            // 用户信息从缓存中移除
            WsUtil.getSessionMap().remove(userId);
        }
    }

    /**
     * 接收客户端消息（用于消息确认，客户端接收消息后，返回确认信号）
     *
     * @param session WebSocketSession
     * @param message 接收到的消息
     * @throws Exception 抛出异常
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 假设客户端返回的消息格式：messageId（需要确认的消息ID）
        String payload = message.getPayload();
        log.info("收到消息：{}", payload);
        WsMessage receivedMessage = JacksonUtil.toBean(payload, WsMessage.class);
        if (receivedMessage != null && receivedMessage.getId() != null) {
            // 收到确认，更新消息状态为“已推送”
            SpringContextHolder.getBean(WsMessageStoreService.class).updateMessageStatus(receivedMessage.getId(), 1);
        }
    }
}
