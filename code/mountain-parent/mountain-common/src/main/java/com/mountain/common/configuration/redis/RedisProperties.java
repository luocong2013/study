package com.mountain.common.configuration.redis;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.redisson.config.ConstantDelay;
import org.redisson.config.DelayStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * redis配置属性类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/21 13:57
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

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

    /**
     * Redisson配置
     */
    private Redisson redisson;


    @Getter
    @Setter
    public static class Cache {

        /**
         * 缓存配置，key为缓存名称，value为过期时间（秒）
         */
        private Map<String, Long> configuration = new HashMap<>(16);
    }

    @Getter
    @Setter
    public static class Redisson {

        /**
         * 是否启用Redisson，默认为true
         */
        private boolean enabled = true;

        /**
         * 看门狗锁超时时间（毫秒）
         */
        private long watchdogTimeout = 30000;

        /**
         * 部署模式，默认为单节点模式
         */
        private RedisMode mode = RedisMode.SINGLE;

        /**
         * 单节点配置
         */
        private SingleConfig single = new SingleConfig();

        /**
         * 集群配置
         */
        private ClusterConfig cluster = new ClusterConfig();

        /**
         * 哨兵配置
         */
        private SentinelConfig sentinel = new SentinelConfig();

        /**
         * 连接池配置
         */
        private PoolConfig pool = new PoolConfig();
    }

    /**
     * 单节点配置
     */
    @Getter
    @Setter
    public static class SingleConfig {

        /**
         * 地址，格式：redis://host:port
         */
        private String address = "redis://localhost:6379";
        /**
         * 用户名
         */
        private String username;
        /**
         * 密码
         */
        private String password;
        /**
         * 数据库索引
         */
        private int database = 0;
    }

    /**
     * 集群配置
     */
    @Getter
    @Setter
    public static class ClusterConfig {

        /**
         * 节点地址，格式：redis://host:port
         */
        private List<String> nodes = new ArrayList<>();
        /**
         * 密码
         */
        private String password;
    }

    /**
     * 哨兵配置
     */
    @Getter
    @Setter
    public static class SentinelConfig {

        /**
         * 主节点名称
         */
        private String masterName = "master";
        /**
         * 哨兵节点地址，格式：redis://host:port
         */
        private List<String> nodes = new ArrayList<>();
        /**
         * 密码
         */
        private String password;
        /**
         * 数据库索引
         */
        private int database = 0;
    }

    /**
     * 连接池配置
     */
    @Getter
    @Setter
    public static class PoolConfig {

        /**
         * 最小空闲连接数
         */
        private int minIdle = 8;
        /**
         * 最大连接数
         */
        private int maxActive = 64;
        /**
         * 连接超时（毫秒）
         */
        private int connectTimeout = 10000;
        /**
         * 空闲连接超时（毫秒）
         */
        private int idleTimeout = 10000;
        /**
         * 重试次数
         */
        private int retryAttempts = 3;
        /**
         * 重试间隔策略
         */
        private DelayStrategy retryDelay = new ConstantDelay(Duration.ofMillis(1500));
    }

    /**
     * Redis部署模式
     */
    public enum RedisMode {
        /**
         * 单节点模式
         */
        SINGLE,
        /**
         * 集群模式
         */
        CLUSTER,
        /**
         * 哨兵模式
         */
        SENTINEL
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
