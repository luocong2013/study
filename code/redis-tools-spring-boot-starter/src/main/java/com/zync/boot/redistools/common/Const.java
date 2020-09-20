package com.zync.boot.redistools.common;

import lombok.experimental.UtilityClass;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 常量类
 * @date 2020/9/19 14:12
 */
@UtilityClass
public class Const {

    /**
     * 释放锁Lua脚本，原子操作
     */
    public static final String LUA_RELEASE_LOCK = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 分布式锁功能名称
     */
    public static final String DISTRIBUTED_LOCK_NAME = "Redis分布式锁测试接口";

    /**
     * 分布式锁的key
     */
    public static final String DISTRIBUTED_LOCK_KEY = "DistributedLockKey";

    /**
     * 分布式锁过期时间
     */
    public static final long DISTRIBUTED_LOCK_KEY_TIMEOUT = 5;

    /**
     * 分布式限流功能名称
     */
    public static final String DISTRIBUTED_LIMIT_NAME = "Redis分布式限流测试接口";

    /**
     * 分布式限流时间范围（单位：秒）
     */
    public static final long DISTRIBUTED_LIMIT_PERIOD = 1;

    /**
     * 分布式限流给定时间范围内可以访问的次数
     */
    public static final long DISTRIBUTED_LIMIT_COUNT = 3;

    /**
     * unknown
     */
    public static final String UNKNOWN = "unknown";

}
