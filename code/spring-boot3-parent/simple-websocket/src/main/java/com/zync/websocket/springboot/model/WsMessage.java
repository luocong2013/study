package com.zync.websocket.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * web socket 消息
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/17 15:46
 **/
@Getter
@Setter
public class WsMessage {

    /**
     * 消息ID
     */
    private Long id;
    /**
     *  消息类型：1-普通消息 2-系统消息
     */
    private Integer type;
    /**
     * 接收用户ID
     */
    @JsonIgnore
    private String userId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息状态：0-未推送，1-已推送，2-推送失败
     */
    @JsonIgnore
    private Integer status;

    public boolean isPushed() {
        return status == 1;
    }

}
