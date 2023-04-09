package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 删除群聊请求消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 12:23
 */
@Data
@ToString(callSuper = true)
public class GroupRemoveRequestMessage extends Message {
    private String groupName;

    public GroupRemoveRequestMessage(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GROUP_REMOVE_REQUEST_MESSAGE;
    }
}
