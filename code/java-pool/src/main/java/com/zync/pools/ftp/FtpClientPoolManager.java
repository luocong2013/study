package com.zync.pools.ftp;

import com.zync.pools.ftp.client.FtpClientExt;
import com.zync.pools.ftp.client.FtpInfo;
import com.zync.pools.ftp.pool.FtpClientPool;
import com.zync.pools.ftp.pool.FtpClientPoolConfig;
import org.apache.commons.pool2.PooledObject;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption FtpClient 连接池管理类，用来初始化和销毁 ftp pool，以及对外提供获取和释放ftp连接
 * @date 2020/2/11 19:09
 */
public class FtpClientPoolManager {

    private FtpClientPool pool;

    public FtpClientPoolManager(FtpClientFactory factory, FtpClientPoolConfig config) {
        this.pool = new FtpClientPool(factory, config.buildPoolConfig());
    }

    /**
     * 获取ftp连接
     * @param ftpInfo
     * @return
     * @throws Exception
     */
    public FtpClientExt getFtpClient(FtpInfo ftpInfo) throws Exception {
        return pool.borrowObject(ftpInfo);
    }

    /**
     * 归还连接到连接池，此处不需要恢复ftp的工作路径
     * 归还对象到连接池时已经进行了还原{@link FtpClientFactory#passivateObject(FtpInfo, PooledObject)}
     * @param ftpInfo
     * @param ftpClient
     */
    public void releaseFtpClient(FtpInfo ftpInfo, FtpClientExt ftpClient) {
        // 此处不需要恢复 ftp 的工作路径
        pool.returnObject(ftpInfo, ftpClient);
    }

    /**
     * 销毁连接池
     */
    public void destroy() {
        if (pool != null) {
            pool.close();
        }
    }

    public FtpClientPool getPool() {
        return pool;
    }
}
