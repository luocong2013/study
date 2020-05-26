package com.redis.redisdemo.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @description redis分布式锁
 * @date 2020/5/26 10:17
 */
@Component
public class RedisLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    /**
     * 释放锁脚本，原子操作
     */
    private static final String RELEASE_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private final RedisTemplate<String, String> redisTemplate;

    public RedisLock(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 加锁
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     * @return
     */
    public Boolean acquire(String key, String value, long expire, TimeUnit timeUnit) {
        try {
            return redisTemplate.boundValueOps(key).setIfAbsent(value, expire, timeUnit);
        } catch (Exception e) {
            logger.error("redis lock error", e);
        }
        return false;
    }

    /**
     * 释放锁
     * @param key
     * @param value
     * @return
     */
    public Boolean release(String key, String value) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.eval(RELEASE_LUA.getBytes(), ReturnType.BOOLEAN, 1, key.getBytes(), value.getBytes()));
    }
}
