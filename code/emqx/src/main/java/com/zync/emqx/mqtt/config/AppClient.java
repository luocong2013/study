package com.zync.emqx.mqtt.config;

import com.zync.emqx.mqtt.client.EmqClient;
import com.zync.emqx.mqtt.enums.QosEnum;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试启动MQTT客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/17 18:02
 */
@Configuration
public class AppClient {

    @Autowired
    private EmqClient emqClient;

    @Autowired
    private MqttProperties properties;

    @PostConstruct
    public void init() {
        emqClient.connect(properties.getUsername(), properties.getPassword());
        emqClient.subscribe("test/#", QosEnum.QoS2);
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(4, new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").build());
        executorService.scheduleWithFixedDelay(() -> emqClient.publish("test/123", "pusblish msg: " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), QosEnum.QoS2, false),
                1, 5, TimeUnit.SECONDS);
    }

}
