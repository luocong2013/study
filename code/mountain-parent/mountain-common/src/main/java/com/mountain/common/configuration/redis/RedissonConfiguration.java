package com.mountain.common.configuration.redis;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Redisson配置类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/23 16:22
 **/
@RequiredArgsConstructor
@SpringBootConfiguration
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(prefix = "redis.redisson", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RedissonConfiguration {

    private final RedisProperties redisProperties;

    /**
     * 配置Redisson客户端
     *
     * @return RedissonClient
     */
    @Bean
    public RedissonClient redissonClient() {
        RedisProperties.Redisson redissonProperties = redisProperties.getRedisson();
        Config config = new Config();
        config.setLockWatchdogTimeout(redissonProperties.getWatchdogTimeout());
        switch (redissonProperties.getMode()) {
            case SINGLE -> configureSingleServer(config);
            case CLUSTER -> configureClusterServers(config);
            case SENTINEL -> configureSentinelServers(config);
            default -> throw new IllegalArgumentException("Unsupported Redisson mode: " + redissonProperties.getMode());
        }
        return Redisson.create(config);
    }

    /**
     * 配置单节点模式
     *
     * @param config Redisson配置
     */
    private void configureSingleServer(Config config) {
        RedisProperties.Redisson redissonProperties = redisProperties.getRedisson();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redissonProperties.getSingle().getAddress())
                .setDatabase(redissonProperties.getSingle().getDatabase());
        if (StrUtil.isNotBlank(redissonProperties.getSingle().getUsername())) {
            config.setUsername(redissonProperties.getSingle().getUsername());
        }
        if (StrUtil.isNotBlank(redissonProperties.getSingle().getPassword())) {
            config.setPassword(redissonProperties.getSingle().getPassword());
        }
        // 配置连接池
        serverConfig.setConnectionMinimumIdleSize(redissonProperties.getPool().getMinIdle())
                .setConnectionPoolSize(redissonProperties.getPool().getMaxActive())
                .setConnectTimeout(redissonProperties.getPool().getConnectTimeout())
                .setIdleConnectionTimeout(redissonProperties.getPool().getIdleTimeout())
                .setRetryAttempts(redissonProperties.getPool().getRetryAttempts())
                .setRetryDelay(redissonProperties.getPool().getRetryDelay());
    }

    /**
     * 配置集群模式
     *
     * @param config Redisson配置
     */
    private void configureClusterServers(Config config) {
        RedisProperties.Redisson redissonProperties = redisProperties.getRedisson();
        ClusterServersConfig serverConfig = config.useClusterServers().addNodeAddress(redissonProperties.getCluster().getNodes().toArray(new String[0]));
        if (StrUtil.isNotBlank(redissonProperties.getSingle().getUsername())) {
            config.setUsername(redissonProperties.getSingle().getUsername());
        }
        if (redissonProperties.getCluster().getPassword() != null && !redissonProperties.getCluster().getPassword()
                .isEmpty()) {
            config.setPassword(redissonProperties.getCluster().getPassword());
        }
        // 配置连接池
        serverConfig.setMasterConnectionMinimumIdleSize(redissonProperties.getPool().getMinIdle())
                .setMasterConnectionPoolSize(redissonProperties.getPool().getMaxActive())
                .setSlaveConnectionMinimumIdleSize(redissonProperties.getPool().getMinIdle())
                .setSlaveConnectionPoolSize(redissonProperties.getPool().getMaxActive())
                .setConnectTimeout(redissonProperties.getPool().getConnectTimeout())
                .setIdleConnectionTimeout(redissonProperties.getPool().getIdleTimeout())
                .setRetryAttempts(redissonProperties.getPool().getRetryAttempts())
                .setRetryDelay(redissonProperties.getPool().getRetryDelay());
    }

    /**
     * 配置哨兵模式
     *
     * @param config Redisson配置
     */
    private void configureSentinelServers(Config config) {
        RedisProperties.Redisson redissonProperties = redisProperties.getRedisson();
        SentinelServersConfig serverConfig = config.useSentinelServers().setMasterName(redissonProperties.getSentinel().getMasterName())
                .addSentinelAddress(redissonProperties.getSentinel().getNodes().toArray(new String[0]))
                .setDatabase(redissonProperties.getSentinel().getDatabase());
        if (redissonProperties.getSentinel().getPassword() != null && !redissonProperties.getSentinel().getPassword().isEmpty()) {
            config.setPassword(redissonProperties.getSentinel().getPassword());
        }
        // 配置连接池
        serverConfig.setMasterConnectionMinimumIdleSize(redissonProperties.getPool().getMinIdle())
                .setMasterConnectionPoolSize(redissonProperties.getPool().getMaxActive())
                .setSlaveConnectionMinimumIdleSize(redissonProperties.getPool().getMinIdle())
                .setSlaveConnectionPoolSize(redissonProperties.getPool().getMaxActive())
                .setConnectTimeout(redissonProperties.getPool().getConnectTimeout())
                .setIdleConnectionTimeout(redissonProperties.getPool().getIdleTimeout())
                .setRetryAttempts(redissonProperties.getPool().getRetryAttempts())
                .setRetryDelay(redissonProperties.getPool().getRetryDelay());
    }

}
