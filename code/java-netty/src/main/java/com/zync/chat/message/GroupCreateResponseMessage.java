package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 创建群聊返回消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:57
 */
@Data
@ToString(callSuper = true)
public class GroupCreateResponseMessage extends AbstractResponseMessage {

    public GroupCreateResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GROUP_CREATE_RESPONSE_MESSAGE;
    }
}
