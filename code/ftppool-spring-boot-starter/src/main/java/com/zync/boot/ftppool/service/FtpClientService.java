package com.zync.boot.ftppool.service;

import com.zync.boot.ftppool.client.FtpClientExt;
import com.zync.boot.ftppool.client.FtpClientProperties;
import com.zync.boot.ftppool.factory.FtpClientFactory;
import com.zync.boot.ftppool.pool.FtpClientPool;
import org.apache.commons.pool2.PooledObject;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption FtpClient服务
 * @date 2020/2/12 16:56
 */
public class FtpClientService {

    /** Ftp Client 连接池*/
    private FtpClientPool pool;

    /**Ftp Client 配置*/
    private FtpClientProperties properties;

    public FtpClientService(FtpClientProperties properties) {
        this.pool = new FtpClientPool(new FtpClientFactory(), properties.getPool().buildPoolConfig());
        this.properties = properties;
    }

    /**
     * 获取 Ftp Client
     * @return
     * @throws Exception
     */
    public FtpClientExt getFtpClient() throws Exception {
        return this.pool.borrowObject(properties);
    }

    /**
     * 归还连接到连接池，此处不需要恢复ftp的工作路径
     * 归还对象到连接池时已经进行了还原{@link FtpClientFactory#passivateObject(FtpClientProperties, PooledObject)}
     * @param ftpClient
     */
    public void releaseFtpClient(FtpClientExt ftpClient) {
        // 此处不需要恢复 ftp 的工作路径
        this.pool.returnObject(properties, ftpClient);
    }
}
