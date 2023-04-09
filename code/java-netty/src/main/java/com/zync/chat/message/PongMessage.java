package com.zync.chat.message;

/**
 * 心跳返回消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 16:25
 */
public class PongMessage extends AbstractResponseMessage {

    public PongMessage() {
        super(true);
    }

    @Override
    public int getMessageType() {
        return PONG_MESSAGE;
    }
}
