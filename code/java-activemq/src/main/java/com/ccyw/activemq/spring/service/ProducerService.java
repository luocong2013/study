package com.ccyw.activemq.spring.service;

/**
 * @author luoc
 * @version V1.0.0
 * @description 发送消息业务接口
 * @date 2018/6/24 17:35
 */
public interface ProducerService {

    /**
     * 发送消息
     * @param message
     */
    void sendMessage(String message);
}
