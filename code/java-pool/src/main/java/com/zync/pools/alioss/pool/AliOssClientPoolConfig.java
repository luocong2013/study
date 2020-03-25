package com.zync.pools.alioss.pool;

import com.aliyun.oss.OSS;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * AliOssClientPoolConfig
 *
 * @author luocong
 * @version V1.0.0
 * @description 提供阿里OSS连接池基本信息的初始化配置
 * @date 2020年03月25日 16:43
 */
@Getter
@Setter
public class AliOssClientPoolConfig {
    /**
     * The default value for the {@code maxTotal} configuration attribute.
     * @see GenericKeyedObjectPool#getMaxTotal()
     */
    private static final int DEFAULT_MAX_TOTAL = -1;
    /**
     * The default value for the {@code maxTotalPerKey} configuration attribute.
     * @see GenericKeyedObjectPool#getMaxTotalPerKey()
     */
    private static final int DEFAULT_MAX_TOTAL_PER_KEY = 8;
    /**
     * The default value for the {@code minIdlePerKey} configuration attribute.
     * @see GenericKeyedObjectPool#getMaxIdlePerKey()
     */
    private static final int DEFAULT_MAX_IDLE_PER_KEY = 8;
    /**
     * The default value for the {@code minIdlePerKey} configuration attribute.
     * @see GenericKeyedObjectPool#getMinIdlePerKey()
     */
    private static final int DEFAULT_MIN_IDLE_PER_KEY = 0;
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

    private int maxTotal = DEFAULT_MAX_TOTAL;

    private int maxTotalPerKey = DEFAULT_MAX_TOTAL_PER_KEY;

    private int maxIdlePerKey = DEFAULT_MAX_IDLE_PER_KEY;

    private int minIdlePerKey = DEFAULT_MIN_IDLE_PER_KEY;

    private long maxWaitMillis = DEFAULT_MAX_WAIT_MILLIS;

    private long timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

    /**
     * 封装GenericKeyedObjectPoolConfig
     * @return
     */
    public GenericKeyedObjectPoolConfig<OSS> buildPoolConfig() {
        GenericKeyedObjectPoolConfig<OSS> config = new GenericKeyedObjectPoolConfig<>();
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
        // 对象池每个key对应最大数
        config.setMaxTotalPerKey(maxTotalPerKey);
        // 对象池每个key对应最大空闲数
        config.setMaxIdlePerKey(maxIdlePerKey);
        // 对象池每个key对应最小空闲数
        config.setMinIdlePerKey(minIdlePerKey);
        // 最大等待时间
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        return config;
    }
}
