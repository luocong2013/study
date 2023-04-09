package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 登录返回消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:55
 */
@Data
@ToString(callSuper = true)
public class LoginResponseMessage extends AbstractResponseMessage {

    public LoginResponseMessage() {
    }

    public LoginResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return LOGIN_RESPONSE_MESSAGE;
    }
}
