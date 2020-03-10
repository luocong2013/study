package com.zync.ftpserver.ftpserverminimal.service;

import com.zync.ftpserver.ftpserverminimal.exception.FtpserverStartFailedException;
import com.zync.ftpserver.ftpserverminimal.util.EncryptUtil;
import com.zync.ftpserver.ftpserverminimal.util.PropertiesUtil;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption ftp服务器启动服务类
 * @date 2020/3/10 21:07
 */
@Component
public class FtpserverService {
    private static final String FTP_USER_CONFIG_PATH = "ftpserver/users.properties";
    private static final String ENCRYPT_TYPE = "encryptType";
    private static final String SERVER_PORT = "serverPort";
    private static final String DEFAULT_LISTENER_NAME = "default";

    /**
     * 启动入口方法
     */
    public void start() {
        FtpServerFactory serverFactory = new FtpServerFactory();
        configListener(serverFactory);
        configUserManager(serverFactory);
        FtpServer server = serverFactory.createServer();
        try {
            server.start();
        } catch (Exception e) {
            throw new FtpserverStartFailedException("ftp server start failed", e);
        }
    }

    private void configListener(FtpServerFactory serverFactory) {
        ListenerFactory factory = new ListenerFactory();
        factory.setPort(Integer.parseInt(PropertiesUtil.get(SERVER_PORT)));
        serverFactory.addListener(DEFAULT_LISTENER_NAME, factory.createListener());
    }

    private void configUserManager(FtpServerFactory serverFactory) {
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File(FTP_USER_CONFIG_PATH));
        String encryptType = PropertiesUtil.get(ENCRYPT_TYPE);
        userManagerFactory.setPasswordEncryptor(EncryptUtil.getEncrypt(encryptType));
        serverFactory.setUserManager(userManagerFactory.createUserManager());
    }
}
