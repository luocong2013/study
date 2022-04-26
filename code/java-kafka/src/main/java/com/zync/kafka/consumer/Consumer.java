package com.zync.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author luocong
 * @description 消费者
 * @date 2020/5/27 13:47
 */
@Component
public class Consumer {

    @KafkaListener(topics = {"logback_to_kafka"})
    public void listen(ConsumerRecord<?, ?> record){
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("---->"+record);
            System.out.println("---->"+message);
        }
    }
}
