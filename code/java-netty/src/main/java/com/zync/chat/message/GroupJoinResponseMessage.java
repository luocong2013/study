package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 加入群聊返回消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:58
 */
@Data
@ToString(callSuper = true)
public class GroupJoinResponseMessage extends AbstractResponseMessage {

    public GroupJoinResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GROUP_JOIN_RESPONSE_MESSAGE;
    }
}
