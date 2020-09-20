package com.zync.boot.redistools.common;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 分布式限流类型
 * @date 2020/9/20 15:38
 */
public enum DistributedLimitType {
    /**
     * 自定义key
     */
    CUSTOMER,
    /**
     * 请求者IP
     */
    IP
}
