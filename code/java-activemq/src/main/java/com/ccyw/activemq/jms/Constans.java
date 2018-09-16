package com.ccyw.activemq.jms;

/**
 * @author luoc
 * @version V1.0.0
 * @description @TODO
 * @date 2018/6/23 19:26
 */
public interface Constans {
    /**
     * ActiveMQ 默认用户名
     */
    String USERNAME = "admin";
    /**
     * ActiveMQ默认登录密码
     */
    String PASSWORD = "admin";
    /**
     * ActiveMQ地址
     */
    String URL = "tcp://192.168.0.105:61616";
    /**
     * 队列名称
     */
    String QUEUE_NAME = "queue-test";
    /**
     * 主题名称
     */
    String TOPIC_NAME = "topic-test";
}
