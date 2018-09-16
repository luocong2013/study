package com.ccyw.activemq.spring.application;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author luoc
 * @version V1.0.0
 * @description 消息消费者
 * @date 2018/6/24 18:18
 */
public class Consumer {

    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jms-consumer.xml");
    }
}
