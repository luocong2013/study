package com.ysyue.im.protocol;

import com.fasterxml.jackson.annotation.JsonValue;
import com.ysyue.im.processor.*;
import com.ysyue.im.holder.SpringContextHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * 消息类型枚举
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/23 9:44
 **/
@Getter
@AllArgsConstructor
public enum MessageType {

    /**
     * 登录请求
     */
    LOGIN((byte) 1, "登录请求", LoginImMessageProcessor.class),

    /**
     * 登录响应
     */
    LOGIN_RESPONSE((byte) 2, "登录响应", null),

    /**
     * 单聊消息
     */
    CHAT((byte) 3, "单聊消息", ChatImMessageProcessor.class),

    /**
     * 群聊消息
     */
    GROUP_CHAT((byte) 4, "群聊消息", GroupChatImMessageProcessor.class),

    /**
     * 消息确认
     */
    ACK((byte) 5, "消息确认", AckImMessageProcessor.class),

    /**
     * 心跳请求
     */
    HEARTBEAT((byte) 6, "心跳请求", HeartbeatImMessageProcessor.class),

    /**
     * 心跳响应
     */
    HEARTBEAT_RESPONSE((byte) 7, "心跳响应", null),

    /**
     * 登出请求
     */
    LOGOUT((byte) 8, "登出请求", LogoutImMessageProcessor.class),

    /**
     * 错误消息
     */
    ERROR((byte) 9, "错误消息", null);

    @JsonValue
    private final byte code;
    private final String desc;
    private final Class<? extends ImMessageProcessor> clazz;

    public ImMessageProcessor getProcessor() {
        Assert.notNull(this.clazz, "Unknown message type: " + this.code);
        return SpringContextHolder.getBean(this.clazz);
    }

    public static MessageType valueOf(byte code) {
        for (MessageType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown message type: " + code);
    }
}
