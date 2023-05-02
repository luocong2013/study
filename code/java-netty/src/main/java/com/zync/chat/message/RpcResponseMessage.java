package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * RPC返回消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/5/2 22:00
 */
@Data
@ToString(callSuper = true)
public class RpcResponseMessage extends AbstractResponseMessage {

    /**
     * 返回值
     */
    private Object returnValue;
    /**
     * 异常值
     */
    private Exception exceptionValue;

    @Override
    public int getMessageType() {
        return RPC_RESPONSE_MESSAGE;
    }
}
