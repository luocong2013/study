package com.zync.pools.ftp;

import com.zync.pools.ftp.client.FtpClientExt;
import com.zync.pools.ftp.client.FtpInfo;
import com.zync.pools.ftp.pool.FtpClientPoolConfig;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption FtpClient测试
 * @date 2020/2/11 19:26
 */
public class FtpClientTest {

    public static void main(String[] args) throws Exception {
        FtpClientFactory factory = new FtpClientFactory();
        FtpClientPoolConfig<FtpClientExt> config = new FtpClientPoolConfig<>();
        config.setMaxTotal(10);

        FtpClientPoolManager manager = new FtpClientPoolManager();
        manager.setFactory(factory);
        manager.setConfig(config);

        FtpInfo ftpInfo = FtpInfo.builder().host("localhost").port(2121).userName("admin").passWord("123456").build();
        FtpClientExt ftpClient = manager.getFtpClient(ftpInfo);
        System.out.println(ftpClient);
        manager.releaseFtpClient(ftpInfo, ftpClient);

        FtpClientExt ftpClient2 = manager.getFtpClient(ftpInfo);
        System.out.println(ftpClient2);
        manager.releaseFtpClient(ftpInfo, ftpClient2);

        //FtpClientExt ftpClient3 = manager.getFtpClient(ftpInfo);
        //System.out.println(ftpClient3);
        //
        //FtpClientExt ftpClient4 = manager.getFtpClient(ftpInfo);
        //System.out.println(ftpClient4);
        //
        //FtpClientExt ftpClient5 = manager.getFtpClient(ftpInfo);
        //System.out.println(ftpClient5);
    }
}
