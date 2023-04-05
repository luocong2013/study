package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 登录请求消息
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:55
 */
@Data
@ToString(callSuper = true)
public class LoginRequestMessage extends Message {
    private String username;
    private String password;
    private String nickname;

    public LoginRequestMessage() {
    }

    public LoginRequestMessage(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    @Override
    public int getMessageType() {
        return LOGIN_REQUEST_MESSAGE;
    }
}
