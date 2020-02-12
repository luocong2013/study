package com.zync.boot.ftppool.pool;

import com.zync.boot.ftppool.client.FtpClientExt;
import com.zync.boot.ftppool.client.FtpClientProperties;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption FtpClient连接池核心类，继承GenericKeyedObjectPool，为FIFO行为实现的对象池
 *             此连接池类似于一个map，根据不同的key(FtpInfo)可以初始化多个连接池
 * @date 2020/2/12 15:40
 */
public class FtpClientPool extends GenericKeyedObjectPool<FtpClientProperties, FtpClientExt> {

    public FtpClientPool(KeyedPooledObjectFactory<FtpClientProperties, FtpClientExt> factory) {
        super(factory);
    }

    public FtpClientPool(KeyedPooledObjectFactory<FtpClientProperties, FtpClientExt> factory, GenericKeyedObjectPoolConfig<FtpClientExt> config) {
        super(factory, config);
    }
}
