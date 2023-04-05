package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 加入群聊请求消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:58
 */
@Data
@ToString(callSuper = true)
public class GroupJoinRequestMessage extends Message {
    private String groupName;

    private String username;

    public GroupJoinRequestMessage(String username, String groupName) {
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public int getMessageType() {
        return GROUP_JOIN_REQUEST_MESSAGE;
    }
}
