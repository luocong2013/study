package com.zync.boot.ftppool.factory;

import com.zync.boot.ftppool.client.FtpClientExt;
import com.zync.boot.ftppool.client.FtpClientProperties;
import com.zync.boot.ftppool.exception.FtpClientCreateException;
import lombok.extern.slf4j.Slf4j;
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
 * @descrption FtpClient工厂类
 * @date 2020/2/12 17:04
 */
@Slf4j
public class FtpClientFactory extends BaseKeyedPooledObjectFactory<FtpClientProperties, FtpClientExt> {

    @Override
    public FtpClientExt create(FtpClientProperties properties) throws Exception {
        FtpClientExt ftpClient = new FtpClientExt();
        boolean hasError = false;
        try {
            // 连接ftp服务器
            ftpClient.connect(properties.getHost(), properties.getPort());
            // 登录ftp服务器
            if (!ftpClient.login(properties.getUsername(), properties.getPassword())) {
                hasError = true;
                String replyStr = ftpClient.getReplyString();
                ftpClient.logout();
                throw new FtpClientCreateException(properties, "ReplyString: " + replyStr);
            }
            // 检测登录是否成功
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                hasError = true;
                String replyStr = ftpClient.getReplyString();
                ftpClient.disconnect();
                throw new FtpClientCreateException(properties, "ReplyString: " + replyStr);
            }
            ftpClient.setBufferSize(properties.getBufSize());
            ftpClient.setConnectTimeout(properties.getConnectTimeout());
            ftpClient.setDataTimeout(properties.getDataTimeout());
            ftpClient.setCharset(properties.getCharset());
            ftpClient.setControlEncoding(properties.getControlEncoding());
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // 设置为被动模式
            ftpClient.enterLocalPassiveMode();
            return ftpClient;
        } catch (SocketTimeoutException e) {
            throw new FtpClientCreateException(properties, "socket timeout exception", e);
        } catch (SocketException e) {
            throw new FtpClientCreateException(properties, "socket exception", e);
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
    public boolean validateObject(FtpClientProperties key, PooledObject<FtpClientExt> p) {
        try {
            FtpClientExt ftpClient = p.getObject();
            boolean validate = ftpClient.sendNoOp();
            if (validate) {
                return ftpClient.printWorkingDirectory() != null;
            }
        } catch (IOException e) {
            log.error("validate ftp client failed.", e);
        }
        return false;
    }

    /**
     * 归还对象到连接池时调用
     * @param key
     * @param p
     * @throws Exception
     */
    @Override
    public void passivateObject(FtpClientProperties key, PooledObject<FtpClientExt> p) throws Exception {
        FtpClientExt ftpClient = p.getObject();
        // 恢复 ftp 连接的默认路径
        ftpClient.changeWorkingDirectory(ftpClient.getDefaultWorkingDir());
    }

    /**
     * 销毁对象时调用
     * @param key
     * @param p
     * @throws Exception
     */
    @Override
    public void destroyObject(FtpClientProperties key, PooledObject<FtpClientExt> p) throws Exception {
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
            log.error("close ftp client failed.", e);
        }
    }
}
