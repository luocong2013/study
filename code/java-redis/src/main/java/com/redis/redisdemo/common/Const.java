package com.redis.redisdemo.common;

/**
 * @author luocong
 * @description 常量类
 * @date 2020/5/26 13:44
 */
public final class Const {

    private Const() {
    }

    /**
     * redis锁key
     */
    public static final String REDIS_LOCK_KEY = "RedisLock";

    /**
     * 初始化令牌桶方法
     */
    public static final String INIT_TOKEN_BUCKET = "initTokenBucket";

    /**
     * 获取当前时间方法
     */
    public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * 请求令牌方法
     */
    public static final String ACQUIRE = "acquire";
}
