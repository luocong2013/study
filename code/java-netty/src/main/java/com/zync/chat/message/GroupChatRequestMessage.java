package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 群聊请求消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 14:00
 */
@Data
@ToString(callSuper = true)
public class GroupChatRequestMessage extends Message {
    private String content;
    private String groupName;
    private String from;

    public GroupChatRequestMessage(String from, String groupName, String content) {
        this.content = content;
        this.groupName = groupName;
        this.from = from;
    }

    @Override
    public int getMessageType() {
        return GROUP_CHAT_REQUEST_MESSAGE;
    }
}
