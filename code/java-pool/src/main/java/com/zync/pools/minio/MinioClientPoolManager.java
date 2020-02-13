package com.zync.pools.minio;

import com.zync.pools.minio.pool.MinioClientPool;
import com.zync.pools.minio.pool.MinioClientPoolConfig;
import io.minio.MinioClient;

/**
 * MinioClientPoolManager
 *
 * @author luocong
 * @version V1.0.0
 * @description MinioClient连接池管理器
 * @date 2020年02月13日 14:32
 */
public class MinioClientPoolManager {

    private MinioClientPool pool;

    public MinioClientPoolManager(MinioClientFactory factory, MinioClientPoolConfig config) {
        this.pool = new MinioClientPool(factory, config.buildPoolConfig());
    }

    /**
     * 获取minio client
     * @return
     * @throws Exception
     */
    public MinioClient getMinioClient() throws Exception {
        return this.pool.borrowObject();
    }

    /**
     * 归还连接到连接池
     * @param minioClient
     */
    public void releaseMinioClient(MinioClient minioClient) {
        this.pool.returnObject(minioClient);
    }

    /**
     * 销毁连接池
     */
    public void destroy() {
        if (this.pool != null) {
            this.pool.close();
        }
    }

}
