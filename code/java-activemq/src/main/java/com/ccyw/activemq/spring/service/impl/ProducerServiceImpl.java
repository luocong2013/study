package com.ccyw.activemq.spring.service.impl;

import com.ccyw.activemq.spring.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.TextMessage;

/**
 * @author luoc
 * @version V1.0.0
 * @description 发送消息业务实现类
 * @date 2018/6/24 17:37
 */
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(String message) {
        // 使用消息模板发送消息
        jmsTemplate.send(session -> {
            // 创建消息
            TextMessage textMessage = session.createTextMessage(message);
            return textMessage;
        });
        System.out.println("发送消息：" + message);
    }
}
