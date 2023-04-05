package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 聊天返回消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:56
 */
@Data
@ToString(callSuper = true)
public class ChatResponseMessage extends AbstractResponseMessage {

    private String from;
    private String content;

    public ChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public ChatResponseMessage(String from, String content) {
        this.from = from;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return CHAT_RESPONSE_MESSAGE;
    }
}
