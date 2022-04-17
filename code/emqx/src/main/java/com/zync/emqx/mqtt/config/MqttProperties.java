package com.zync.emqx.mqtt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MQTT客户端属性
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/17 17:41
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {
    /**
     * broker url
     */
    private String brokerUrl;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
