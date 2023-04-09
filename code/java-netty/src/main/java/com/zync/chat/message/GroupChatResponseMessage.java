package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 群聊返回消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 14:00
 */
@Data
@ToString(callSuper = true)
public class GroupChatResponseMessage extends AbstractResponseMessage {
    private String from;
    private String content;

    public GroupChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupChatResponseMessage(String from, String content) {
        super(true);
        this.from = from;
        this.content = content;
    }
    @Override
    public int getMessageType() {
        return GROUP_CHAT_RESPONSE_MESSAGE;
    }
}
