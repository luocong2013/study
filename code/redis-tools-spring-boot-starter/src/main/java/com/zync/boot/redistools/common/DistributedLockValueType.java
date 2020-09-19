package com.zync.boot.redistools.common;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 分布式锁值的类型
 * @date 2020/9/19 20:44
 */
public enum DistributedLockValueType {
    /**
     * 自定义值
     */
    CUSTOMER,
    /**
     * UUID
     */
    UUID,
    /**
     * 雪花算法生成的分布式ID
     */
    SNOWFLAKE
}
