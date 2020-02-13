package com.zync.pools.minio.pool;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * MinioClientPoolConfig
 *
 * @author luocong
 * @version V1.0.0
 * @description 提供minio连接池基本信息的初始化配置
 * @date 2020年02月13日 14:18
 */
@Getter
@Setter
public class MinioClientPoolConfig {
    /**
     * The default value for the {@code maxTotal} configuration attribute.
     * @see GenericObjectPool#getMaxTotal()
     */
    public static final int DEFAULT_MAX_TOTAL = 8;
    /**
     * The default value for the {@code maxIdle} configuration attribute.
     * @see GenericObjectPool#getMaxIdle()
     */
    public static final int DEFAULT_MAX_IDLE = 8;
    /**
     * The default value for the {@code minIdle} configuration attribute.
     * @see GenericObjectPool#getMinIdle()
     */
    public static final int DEFAULT_MIN_IDLE = 0;
    /**
     * The default value for the {@code maxWait} configuration attribute.
     * @see GenericObjectPool#getMaxWaitMillis()
     * @see GenericKeyedObjectPool#getMaxWaitMillis()
     */
    public static final long DEFAULT_MAX_WAIT_MILLIS = -1L;
    /**
     * The default value for the {@code timeBetweenEvictionRunsMillis} configuration attribute.
     * @see GenericKeyedObjectPoolConfig#getTimeBetweenEvictionRunsMillis()
     */
    private static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 1000 * 10;

    private int maxTotal = DEFAULT_MAX_TOTAL;

    private int maxIdle = DEFAULT_MAX_IDLE;

    private int minIdle = DEFAULT_MIN_IDLE;

    private long maxWaitMillis = DEFAULT_MAX_WAIT_MILLIS;

    private long timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

    /**
     * 封装GenericKeyedObjectPoolConfig
     * @return
     */
    public GenericObjectPoolConfig<MinioClient> buildPoolConfig() {
        GenericObjectPoolConfig<MinioClient> config = new GenericObjectPoolConfig<>();
        // 后进先出
        config.setLifo(false);
        // 取对象时验证
        config.setTestOnBorrow(true);
        // 回收时验证
        config.setTestOnReturn(true);
        // 创建时验证
        config.setTestOnCreate(true);
        // 空闲时验证
        config.setTestWhileIdle(true);
        // 最大数
        config.setMaxTotal(maxTotal);
        // 最大空闲
        config.setMaxIdle(maxIdle);
        // 最小空闲
        config.setMinIdle(minIdle);
        // 最大空闲
        config.setMaxIdle(20);
        // 最大等待时间
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        return config;
    }
}
