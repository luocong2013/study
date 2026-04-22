package com.mountain.common.configuration.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * redis缓存配置类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/21 13:54
 **/
@EnableCaching
@RequiredArgsConstructor
@SpringBootConfiguration
@EnableConfigurationProperties(RedisCacheProperties.class)
public class RedisCacheConfiguration {

    private final RedisCacheProperties redisCacheProperties;

    /**
     * 主缓存管理器，根据配置决定使用Redis还是内存作为缓存
     *
     * @param redisConnectionFactory Redis连接工厂
     * @return 缓存管理器
     */
    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 如果启用了二级缓存，返回组合缓存管理器
        if (redisCacheProperties.isEnableTwoLevelCache()) {
            return createCompositeCacheManager(redisConnectionFactory);
        }
        return switch (redisCacheProperties.getMode()) {
            case REDIS -> createRedisCacheManager(redisConnectionFactory);
            case MEMORY -> createMemoryCacheManager();
        };
    }

    /**
     * 创建组合缓存管理器（二级缓存：内存+Redis）
     *
     * @param redisConnectionFactory Redis连接工厂
     * @return 组合缓存管理器
     */
    private CacheManager createCompositeCacheManager(RedisConnectionFactory redisConnectionFactory) {
        List<CacheManager> cacheManagers = new ArrayList<>();
        // 添加内存缓存管理器作为一级缓存
        cacheManagers.add(createMemoryCacheManager());
        // 添加Redis缓存管理器作为二级缓存
        cacheManagers.add(createRedisCacheManager(redisConnectionFactory));
        CompositeCacheManager cacheManager = new CompositeCacheManager();
        cacheManager.setCacheManagers(cacheManagers);
        cacheManager.setFallbackToNoOpCache(false);
        return cacheManager;
    }

    /**
     * 创建Redis缓存管理器
     *
     * @param redisConnectionFactory Redis连接工厂
     * @return Redis缓存管理器
     */
    private CacheManager createRedisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 默认配置
        org.springframework.data.redis.cache.RedisCacheConfiguration defaultConfig = org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig()
                // 设置默认过期时间
                .entryTtl(Duration.ofSeconds(redisCacheProperties.getDefaultExpireTime()))
                .prefixCacheNameWith(redisCacheProperties.getCachePrefix())
                .serializeKeysWith(keyPair())
                .serializeValuesWith(valuePair());

        // 自定义特定缓存的配置
        Map<String, org.springframework.data.redis.cache.RedisCacheConfiguration> cacheConfigurations = new HashMap<>(16);
        redisCacheProperties.getCache().getConfiguration().forEach((key, value) ->
                cacheConfigurations.put(key, defaultConfig.entryTtl(Duration.ofSeconds(value))));
        if (cacheConfigurations.isEmpty()) {
            cacheConfigurations.put("user", defaultConfig.entryTtl(Duration.ofHours(4)));
        }

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .transactionAware()
                .build();
    }

    /**
     * 创建内存缓存管理器
     *
     * @return 内存缓存管理器
     */
    private CacheManager createMemoryCacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }

    /**
     * 配置键序列化
     */
    private RedisSerializationContext.SerializationPair<String> keyPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());
    }

    /**
     * 配置值序列化
     */
    private RedisSerializationContext.SerializationPair<Object> valuePair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());
    }
}
