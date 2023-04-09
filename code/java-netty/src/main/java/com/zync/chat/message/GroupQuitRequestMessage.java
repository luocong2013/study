package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 退出群聊请求消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:59
 */
@Data
@ToString(callSuper = true)
public class GroupQuitRequestMessage extends Message {
    private String groupName;

    private String username;

    public GroupQuitRequestMessage() {
    }

    public GroupQuitRequestMessage(String username, String groupName) {
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public int getMessageType() {
        return GROUP_QUIT_REQUEST_MESSAGE;
    }
}
