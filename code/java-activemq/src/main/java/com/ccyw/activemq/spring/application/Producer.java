package com.ccyw.activemq.spring.application;

import com.ccyw.activemq.spring.service.ProducerService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author luoc
 * @version V1.0.0
 * @description 消息提供者
 * @date 2018/6/24 18:13
 */
public class Producer {

    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jms-producer.xml");
        ProducerService service = (ProducerService) context.getBean("producerService");
        for (int i = 1; i <= 100; i++) {
            service.sendMessage("test" + i);
        }
        // 关闭资源
        context.close();
    }
}
