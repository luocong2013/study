package com.zync.boot.ftppool.client;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Ftp配置文件类
 * @date 2020/2/12 12:53
 */
@Data
@ConfigurationProperties(prefix = FtpClientProperties.PREFIX)
public class FtpClientProperties {

    public static final String PREFIX = "spring.ftp";

    /**
     * 默认端口
     */
    private static final int DEFAULT_FTP_PORT = 21;
    /**
     * 默认 buffer size
     */
    private static final int DEFAULT_BUFFER_SIZE = 16384;
    /**
     * ftp client连接超时时间，如果10秒以内没连上，就超时
     */
    private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
    /**
     * 默认ftp DataTimeout是5分钟，注意，不是说5分钟必须传完，是两个packet之间的时间间隔不能超过5分钟
     * 设这么大是因为原来是无限长，暂不敢改太小
     */
    private static final int DEFAULT_DATA_TIMEOUT = 300000;
    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 连接池
     */
    private Pool pool = new Pool();
    /**
     * 是否启用
     */
    private boolean enabled;
    /**
     * 地址
     */
    private String host;
    /**
     * 端口
     */
    private int port = DEFAULT_FTP_PORT;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * buffer size
     */
    private int bufSize = DEFAULT_BUFFER_SIZE;
    /**
     * 连接超时时间
     */
    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    /**
     * DataTimeout
     */
    private int dataTimeout = DEFAULT_DATA_TIMEOUT;
    /**
     * 字符集
     */
    private Charset charset = DEFAULT_CHARSET;
    /**
     * 由FTP控件连接使用的字符编码
     */
    private String controlEncoding = DEFAULT_CHARSET.toString();

    /**
     * 连接池属性
     */
    @Getter
    @Setter
    public static class Pool {
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
        public GenericKeyedObjectPoolConfig<FtpClientExt> buildPoolConfig() {
            GenericKeyedObjectPoolConfig<FtpClientExt> config = new GenericKeyedObjectPoolConfig<>();
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
}
