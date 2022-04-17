package com.zync.emqx.mqtt.client;

import com.zync.emqx.mqtt.config.MqttProperties;
import com.zync.emqx.mqtt.enums.QosEnum;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * MQTT客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/17 17:33
 */
@Slf4j
@Component
public class EmqClient {

    private IMqttClient client;

    @Autowired
    private MqttProperties properties;

    @Autowired
    private MqttCallback callback;

    @PostConstruct
    public void init() {
        MqttClientPersistence persistence = new MemoryPersistence();
        try {
            log.info("初始化客户端MqttClient对象");
            client = new MqttClient(properties.getBrokerUrl(), properties.getClientId(), persistence);
        } catch (MqttException e) {
            log.error("初始化客户端MqttClient对象失败, brokerUrl={}, clientId={}", properties.getBrokerUrl(), properties.getClientId(), e);
        }
    }

    /**
     * 连接broker
     *
     * @param username
     * @param password
     */
    public void connect(String username, String password) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setCleanSession(true);

        // 设置回调
        client.setCallback(callback);

        try {
            log.info("Mqtt客户端开始连接服务端");
            client.connect(options);
        } catch (MqttException e) {
            log.error("mqtt客户端连接服务端失败", e);
        }
    }

    /**
     * 断开连接
     */
    @PreDestroy
    public void disConnect() {
        try {
            log.info("Mqtt客户端与服务端断开连接");
            client.disconnect();
        } catch (MqttException e) {
            log.error("断开连接产生异常", e);
        }
    }

    /**
     * 重连
     */
    public void reConnect() {
        try {
            log.info("Mqtt客户端重连");
            client.reconnect();
        } catch (MqttException e) {
            log.error("重连失败", e);
        }
    }

    /**
     * 发布消息
     *
     * @param topic
     * @param msg
     * @param qos
     * @param retain
     */
    public void publish(String topic, String msg, QosEnum qos, boolean retain) {

        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(msg.getBytes());
        mqttMessage.setQos(qos.value());
        mqttMessage.setRetained(retain);
        try {
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            log.error("发布消息失败, topic={}, msg={}, qos={}, retain={}", topic, msg, qos.value(), retain, e);
        }

    }

    /**
     * 订阅
     *
     * @param topicFilter
     * @param qos
     */
    public void subscribe(String topicFilter, QosEnum qos) {
        try {
            client.subscribe(topicFilter, qos.value());
        } catch (MqttException e) {
            log.error("订阅主题失败, topicFilter={}, qos={}", topicFilter, qos.value(), e);
        }

    }

    /**
     * 取消订阅
     *
     * @param topicFilter
     */
    public void unSubscribe(String topicFilter) {
        try {
            client.unsubscribe(topicFilter);
        } catch (MqttException e) {
            log.error("取消订阅失败, topicfiler={}", topicFilter, e);
        }
    }

}
