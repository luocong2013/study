package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 获取群聊成员请求消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 14:01
 */
@Data
@ToString(callSuper = true)
public class GroupMembersRequestMessage extends Message {
    private String groupName;

    public GroupMembersRequestMessage() {
    }

    public GroupMembersRequestMessage(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GROUP_MEMBERS_REQUEST_MESSAGE;
    }
}
