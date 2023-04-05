package com.zync.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * 自定义消息-返回消息抽象类型
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:54
 */
@Data
@ToString(callSuper = true)
public abstract class AbstractResponseMessage extends Message {
    private boolean success;
    private String reason;

    public AbstractResponseMessage() {
    }

    public AbstractResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
}
