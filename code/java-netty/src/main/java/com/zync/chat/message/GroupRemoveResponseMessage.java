package com.zync.chat.message;

/**
 * 删除群聊返回消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 12:25
 */
public class GroupRemoveResponseMessage extends AbstractResponseMessage {

    public GroupRemoveResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GROUP_REMOVE_RESPONSE_MESSAGE;
    }
}
