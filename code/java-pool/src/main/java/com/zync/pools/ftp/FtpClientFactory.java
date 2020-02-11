package com.zync.pools.ftp;

import com.zync.pools.ftp.client.FtpClientExt;
import com.zync.pools.ftp.client.FtpInfo;
import com.zync.pools.ftp.exception.FtpClientCreateException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption FTPClient工厂类
 * @date 2020/2/11 16:23
 */
public class FtpClientFactory extends BaseKeyedPooledObjectFactory<FtpInfo, FtpClientExt> {

    @Override
    public FtpClientExt create(FtpInfo ftpInfo) throws Exception {
        FtpClientExt ftpClient = new FtpClientExt();
        boolean hasError = false;
        try {
            // 连接ftp服务器
            ftpClient.connect(ftpInfo.getHost(), ftpInfo.getPort());
            // 登录ftp服务器
            if (!ftpClient.login(ftpInfo.getUserName(), ftpInfo.getPassWord())) {
                hasError = true;
                String replyStr = ftpClient.getReplyString();
                ftpClient.logout();
                throw new FtpClientCreateException(ftpInfo, "ReplyString: " + replyStr);
            }
            // 检测登录是否成功
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                hasError = true;
                String replyStr = ftpClient.getReplyString();
                ftpClient.disconnect();
                throw new FtpClientCreateException(ftpInfo, "ReplyString: " + replyStr);
            }
            ftpClient.setBufferSize(ftpInfo.getBufSize());
            ftpClient.setConnectTimeout(ftpInfo.getConnectTimeout());
            ftpClient.setDataTimeout(ftpInfo.getDataTimeout());
            ftpClient.setCharset(ftpInfo.getCharset());
            ftpClient.setControlEncoding(ftpInfo.getControlEncoding());
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // 设置为被动模式
            ftpClient.enterLocalPassiveMode();
            return ftpClient;
        } catch (SocketTimeoutException e) {
            throw new FtpClientCreateException(ftpInfo, "socket timeout exception", e);
        } catch (SocketException e) {
            throw new FtpClientCreateException(ftpInfo, "socket exception", e);
        } finally {
            // 如果发生异常，关闭已经创建的连接
            if (hasError) {
                closeConn(ftpClient);
            }
        }
    }

    @Override
    public PooledObject<FtpClientExt> wrap(FtpClientExt ftpClient) {
        return new DefaultPooledObject<>(ftpClient);
    }

    @Override
    public boolean validateObject(FtpInfo key, PooledObject<FtpClientExt> p) {
        try {
            FtpClientExt ftpClient = p.getObject();
            // 向服务器发送请求，验证连接是否正常
            boolean validate = ftpClient.sendNoOp();
            if (validate) {
                return ftpClient.printWorkingDirectory() != null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 激活对象时调用
     * 记录 ftp 连接的默认路径
     * @param key
     * @param p
     * @throws Exception
     */
    @Override
    public void activateObject(FtpInfo key, PooledObject<FtpClientExt> p) throws Exception {
        FtpClientExt ftpClient = p.getObject();
        ftpClient.setDefaultWorkingDir(ftpClient.printWorkingDirectory());
        System.out.println("activate...");
    }

    /**
     * 归还对象到连接池时调用
     * 恢复 ftp 连接的默认路径
     * @param key
     * @param p
     * @throws Exception
     */
    @Override
    public void passivateObject(FtpInfo key, PooledObject<FtpClientExt> p) throws Exception {
        FtpClientExt ftpClient = p.getObject();
        ftpClient.changeWorkingDirectory(ftpClient.getDefaultWorkingDir());
        System.out.println("passivate...");
    }

    /**
     * 销毁对象时调用
     * @param key
     * @param p
     * @throws Exception
     */
    @Override
    public void destroyObject(FtpInfo key, PooledObject<FtpClientExt> p) throws Exception {
        closeConn(p.getObject());
    }

    /**
     * 销毁ftp连接
     * @param ftpClient
     */
    private void closeConn(FtpClientExt ftpClient) {
        if (ftpClient == null) {
            return;
        }
        try {
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
