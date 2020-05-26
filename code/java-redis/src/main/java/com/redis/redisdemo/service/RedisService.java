package com.redis.redisdemo.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @description
 * @date 2020/5/25 18:02
 */
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**========================================字符串================================================**/
    public void setValue(String key, String value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    public void setValueEx(String key, String value, long expire, TimeUnit timeUnit) {
        redisTemplate.boundValueOps(key).set(value, expire, timeUnit);
    }

    public void setValueNEx(String key, String value, long expire, TimeUnit timeUnit) {
        redisTemplate.boundValueOps(key).setIfAbsent(value, expire, timeUnit);
    }

    public String getValue(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * boundXXXOps 与 opsForXXX 的区别：
     * 1. boundXXXOps 只能对单个key进行细微的操作，源码是封装了opsForXXX的操作
     * 2. opsForXXX 可以对多个key进行操作
     */
    public void setValueOps(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }


    /**========================================list================================================**/
    public void setListValue(String key, String value) {
        redisTemplate.boundListOps(key).rightPush(value);
    }

    public List<String> getListValue(String key) {
        return redisTemplate.boundListOps(key).range(0, -1);
    }


    /**========================================hash================================================**/
    public void setHashValue(String key, Object hashKey, Object value) {
        redisTemplate.boundHashOps(key).put(hashKey, value);
    }

    public Map<Object, Object> getHashValue(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }


    /**========================================set================================================**/
    public void setSetValue(String key, String value) {
        redisTemplate.boundSetOps(key).add(value);
    }

    public Set<String> getSetValue(String key) {
        return redisTemplate.boundSetOps(key).members();
    }


    /**========================================zset================================================**/
    public void setZSetValue(String key, String value, double score) {
        redisTemplate.boundZSetOps(key).add(value, score);
    }

    public Set<String> getZSetValue(String key, double min, double max) {
        return redisTemplate.boundZSetOps(key).rangeByScore(min, max);
    }
}
