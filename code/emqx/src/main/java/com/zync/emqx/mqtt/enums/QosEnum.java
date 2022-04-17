package com.zync.emqx.mqtt.enums;

/**
 * Qos（服务质量）枚举
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/17 17:43
 */
public enum QosEnum {
    /**
     * QoS 0（最多一次）
     */
    QoS0(0),
    /**
     * QoS 1（至少一次）
     */
    QoS1(1),
    /**
     * QoS 2（仅一次）
     */
    QoS2(2)
    ;


    private final int value;

    QosEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
