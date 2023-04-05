package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 退出群聊返回消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:59
 */
@Data
@ToString(callSuper = true)
public class GroupQuitResponseMessage extends AbstractResponseMessage {
    public GroupQuitResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GROUP_QUIT_RESPONSE_MESSAGE;
    }
}
