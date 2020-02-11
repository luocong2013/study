package com.zync.pools.ftp.pool;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author luocong
 * @version V1.0.0
 * @descrption FtpClient连接池核心类，继承GenericKeyedObjectPool，为FIFO行为实现的对象池
 *             此连接池类似于一个map，根据不同的key(FtpInfo)可以初始化多个连接池
 * @date 2020/2/11 15:19
 */
public class FtpClientPool<K, T> extends GenericKeyedObjectPool<K, T> {

    public FtpClientPool(KeyedPooledObjectFactory<K, T> factory) {
        super(factory);
    }

    public FtpClientPool(KeyedPooledObjectFactory<K, T> factory, GenericKeyedObjectPoolConfig<T> config) {
        super(factory, config);
    }
}
