package com.zync.boot.redistools.common;

/**
 * @author luocong
 * @version V1.0.0
 * @descrption 分布式限流key的类型
 * @date 2020/9/20 15:38
 */
public enum DistributedLimitKeyType {
    /**
     * 自定义key
     */
    CUSTOMER,
    /**
     * 请求者IP
     */
    IP
}
