package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * 创建群聊请求消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:57
 */
@Data
@ToString(callSuper = true)
public class GroupCreateRequestMessage extends Message {
    private String groupName;
    private Set<String> members;

    public GroupCreateRequestMessage() {
    }

    public GroupCreateRequestMessage(String groupName, Set<String> members) {
        this.groupName = groupName;
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GROUP_CREATE_REQUEST_MESSAGE;
    }
}
