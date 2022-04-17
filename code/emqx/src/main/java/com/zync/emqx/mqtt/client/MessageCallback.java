package com.zync.emqx.mqtt.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * MQTT消息回调
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/17 17:46
 */
@Slf4j
@Component
public class MessageCallback implements MqttCallbackExtended {

    /**
     * 当与服务器的连接成功完成时的回调
     * @param reconnect 如果为true，则连接是自动重新连接的结果
     * @param serverURI 建立连接的服务器 URI
     */
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("连接服务器成功, reconnect={}, serverUri={}", reconnect, serverURI);
    }

    /**
     * 丢失了对服务端的连接后触发的回调
     * @param cause
     */
    @Override
    public void connectionLost(Throwable cause) {
        // 资源的清理  重连
        log.info("丢失了对服务端的连接");
    }

    /**
     * 应用收到消息后触发的回调
     * @param topic
     * @param message
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("订阅者订阅到了消息, topic={}, messageid={}, qos={}, payload={}",
                topic,
                message.getId(),
                message.getQos(),
                new String(message.getPayload()));
    }

    /**
     * 消息发布者消息发布完成产生的回调
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        int messageId = token.getMessageId();
        String[] topics = token.getTopics();
        log.info("消息发布完成, messageid={}, topics={}",messageId,topics);
    }

}
