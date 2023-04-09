package com.zync.chat.message;

/**
 * 心跳请求消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 16:24
 */
public class PingMessage extends Message {

    @Override
    public int getMessageType() {
        return PING_MESSAGE;
    }
}
