package com.zync.pools.minio.pool;

import io.minio.MinioClient;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * MinioClientPool
 *
 * @author luocong
 * @version V1.0.0
 * @description MinioClient连接池核心类
 * @date 2020年02月13日 14:16
 */
public class MinioClientPool extends GenericObjectPool<MinioClient> {

    public MinioClientPool(PooledObjectFactory<MinioClient> factory) {
        super(factory);
    }

    public MinioClientPool(PooledObjectFactory<MinioClient> factory, GenericObjectPoolConfig<MinioClient> config) {
        super(factory, config);
    }

    public MinioClientPool(PooledObjectFactory<MinioClient> factory, GenericObjectPoolConfig<MinioClient> config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}
