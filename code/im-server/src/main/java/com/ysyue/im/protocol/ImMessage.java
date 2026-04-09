package com.ysyue.im.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * IM 消息协议封装
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/23 9:43
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 消息类型
     */
    private MessageType type;

    /**
     * 发送者 ID
     */
    private String senderId;

    /**
     * 接收者 ID（单聊时为对方 ID，群聊时为群组 ID）
     */
    private String receiverId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息 ID（用于去重和确认）
     */
    private String messageId;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 扩展字段
     */
    private String ext;
}
