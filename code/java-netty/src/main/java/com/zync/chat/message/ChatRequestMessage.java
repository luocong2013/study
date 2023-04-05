package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 聊天请求消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:56
 */
@Data
@ToString(callSuper = true)
public class ChatRequestMessage extends Message {
    private String content;
    private String to;
    private String from;

    public ChatRequestMessage() {
    }

    public ChatRequestMessage(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return CHAT_REQUEST_MESSAGE;
    }
}
