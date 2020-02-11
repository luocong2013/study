package com.zync.pools.ftp.pool;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 提供ftp连接池基本信息的初始化配置
 * @date 2020/2/11 18:01
 */
@Getter
@Setter
public class FtpClientPoolConfig<T> {
    /**
     * The default value for the {@code maxTotalPerKey} configuration attribute.
     * @see GenericKeyedObjectPool#getMaxTotalPerKey()
     */
    private static final int DEFAULT_MAX_TOTAL_PER_KEY = -1;
    /**
     * The default value for the {@code maxTotal} configuration attribute.
     * @see GenericKeyedObjectPool#getMaxTotal()
     */
    private static final int DEFAULT_MAX_TOTAL = -1;
    /**
     * The default value for the {@code minIdlePerKey} configuration attribute.
     * @see GenericKeyedObjectPool#getMinIdlePerKey()
     */
    private static final int DEFAULT_MIN_IDLE_PER_KEY = 20;
    /**
     * The default value for the {@code minIdlePerKey} configuration attribute.
     * @see GenericKeyedObjectPool#getMaxIdlePerKey()
     */
    private static final int DEFAULT_MAX_IDLE_PER_KEY = 50;
    /**
     * The default value for the {@code maxWaitMillis} configuration attribute.
     * @see GenericKeyedObjectPool#getMaxWaitMillis()
     */
    private static final long DEFAULT_MAX_WAIT_MILLIS = -1L;
    /**
     * The default value for the {@code timeBetweenEvictionRunsMillis} configuration attribute.
     * @see GenericKeyedObjectPoolConfig#getTimeBetweenEvictionRunsMillis()
     */
    private static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 1000 * 10;

    private int maxTotalPerKey = DEFAULT_MAX_TOTAL_PER_KEY;

    private int maxTotal = DEFAULT_MAX_TOTAL;

    private int minIdlePerKey = DEFAULT_MIN_IDLE_PER_KEY;

    private int maxIdlePerKey = DEFAULT_MAX_IDLE_PER_KEY;

    private long maxWaitMillis = DEFAULT_MAX_WAIT_MILLIS;

    private long timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

    /**
     * 封装GenericKeyedObjectPoolConfig
     * @return
     */
    public GenericKeyedObjectPoolConfig<T> buildPoolConfig() {
        GenericKeyedObjectPoolConfig<T> config = new GenericKeyedObjectPoolConfig<>();
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);
        config.setMaxTotalPerKey(maxTotalPerKey);
        config.setMaxTotal(maxTotal);
        config.setMinIdlePerKey(minIdlePerKey);
        config.setMaxIdlePerKey(maxIdlePerKey);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        return config;
    }
}
