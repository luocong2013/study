package com.zync.kafka.producter;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author luocong
 * @description 生产者
 * @date 2020/5/27 13:40
 */
@Component
public class Producter {

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * 发送消息方法
     */
    public void send() {
        Message message = new Message();
        message.setId("KFK_"+System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());

        kafkaTemplate.send("test", JSON.toJSONString(message));
    }
}
