package com.mountain.common.holder;

import com.mountain.common.configuration.redis.RedisCacheProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis缓存工具类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/21 14:55
 **/
@Component
@RequiredArgsConstructor
public class RedisCacheHolder {

    private final RedisCacheProperties redisCacheProperties;
    private final RedisTemplate<String, Object> redisTemplate;

    // todo
}
