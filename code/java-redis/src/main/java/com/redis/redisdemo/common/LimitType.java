package com.redis.redisdemo.common;

/**
 * @author luocong
 * @description 限流类型
 * @date 2020/5/28 17:47
 */
public enum LimitType {
    /**
     * 自定义key
     */
    CUSTOMER,
    /**
     * 请求者IP
     */
    IP
}
