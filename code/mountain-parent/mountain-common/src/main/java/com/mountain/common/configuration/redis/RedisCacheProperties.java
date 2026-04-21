package com.mountain.common.configuration.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * redis缓存配置属性类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/21 13:57
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "redis")
public class RedisCacheProperties {

    /**
     * 缓存前缀
     */
    private String cachePrefix = "mountain:";

    /**
     * 默认缓存过期时间（秒），默认为1800秒（30分钟）
     */
    private long defaultExpireTime = 1800;

    /**
     * 缓存模式：redis或memory
     */
    private CacheMode mode = CacheMode.REDIS;

    /**
     * 是否启用二级缓存（先查内存，再查Redis）
     */
    private boolean enableTwoLevelCache = false;

    /**
     * 缓存配置
     */
    private Cache cache;

    @Getter
    @Setter
    public static class Cache {

        /**
         * 缓存配置，key为缓存名称，value为过期时间（秒）
         */
        private Map<String, Long> configuration = new HashMap<>(16);
    }

    /**
     * 缓存模式枚举
     */
    public enum CacheMode {
        /**
         * redis
         */
        REDIS,
        /**
         * memory
         */
        MEMORY
    }
}
