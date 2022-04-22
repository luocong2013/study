package com.zync.emqx;

import com.zync.emqx.mqtt.client.EmqClient;
import com.zync.emqx.mqtt.config.MqttProperties;
import com.zync.emqx.mqtt.enums.QosEnum;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * EMQX 客户端 paho 测试 启动类
 * @author luocong
 */
@SpringBootApplication(scanBasePackages = {"com.zync.emqx.mqtt"})
public class EmqxClientApp {

    public static void main(String[] args) {
        SpringApplication.run(EmqxClientApp.class, args);
    }

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
