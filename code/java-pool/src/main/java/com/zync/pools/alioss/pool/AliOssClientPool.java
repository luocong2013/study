package com.zync.pools.alioss.pool;

import com.aliyun.oss.OSS;
import com.zync.pools.alioss.client.AliOssInfo;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * AliOssClientPool
 *
 * @author luocong
 * @version V1.0.0
 * @description 阿里OSS连接池核心类，继承GenericKeyedObjectPool，为FIFO行为实现的对象池
 *         此连接池类似于一个map，根据不同的key(FtpInfo)可以初始化多个连接池
 * @date 2020年03月25日 16:18
 */
public class AliOssClientPool extends GenericKeyedObjectPool<AliOssInfo, OSS> {

    public AliOssClientPool(KeyedPooledObjectFactory<AliOssInfo, OSS> factory) {
        super(factory);
    }

    public AliOssClientPool(KeyedPooledObjectFactory<AliOssInfo, OSS> factory, GenericKeyedObjectPoolConfig<OSS> config) {
        super(factory, config);
    }
}
