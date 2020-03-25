package com.zync.pools.alioss;

import com.aliyun.oss.OSS;
import com.zync.pools.alioss.client.AliOssInfo;
import com.zync.pools.alioss.pool.AliOssClientPool;
import com.zync.pools.alioss.pool.AliOssClientPoolConfig;

/**
 * AliOssClientPoolManager
 *
 * @author luocong
 * @version V1.0.0
 * @description OssClient连接池管理类，用来初始化和销毁oss pool
 * @date 2020年03月25日 16:54
 */
public class AliOssClientPoolManager {

    private AliOssClientPool pool;

    public AliOssClientPoolManager(AliOssClientFactory factory, AliOssClientPoolConfig config) {
        this.pool = new AliOssClientPool(factory, config.buildPoolConfig());
    }

    /**
     * 获取oss连接
     * @param ossInfo
     * @return
     * @throws Exception
     */
    public OSS getOssClient(AliOssInfo ossInfo) throws Exception {
        return pool.borrowObject(ossInfo);
    }

    /**
     * 归还连接到连接池
     * @param ossInfo
     * @param ossClient
     */
    public void releaseOssClient(AliOssInfo ossInfo, OSS ossClient) {
        pool.returnObject(ossInfo, ossClient);
    }

    public void destroy() {
        if (pool != null) {
            pool.close();
        }
    }

    public AliOssClientPool getPool() {
        return pool;
    }
}
