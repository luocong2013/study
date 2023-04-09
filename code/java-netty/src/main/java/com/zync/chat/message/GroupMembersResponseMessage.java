package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * 获取群聊成员返回消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 14:01
 */
@Data
@ToString(callSuper = true)
public class GroupMembersResponseMessage extends AbstractResponseMessage {

    private Set<String> members;

    public GroupMembersResponseMessage(Set<String> members) {
        super(true);
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GROUP_MEMBERS_RESPONSE_MESSAGE;
    }
}
