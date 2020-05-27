package com.zync.kafka.producter;

import java.util.Date;

/**
 * @author luocong
 * @description 消息
 * @date 2020/5/27 11:42
 */
public class Message {

    private String id;

    private String msg;

    private Date sendTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
