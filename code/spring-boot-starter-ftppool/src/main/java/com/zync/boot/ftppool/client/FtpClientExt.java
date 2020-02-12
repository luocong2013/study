package com.zync.boot.ftppool.client;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 继承 org.apache.commons.net.ftp.FTPClient
 *             增加defaultWorkingDir，便于使用ftp client连接池时，支持复位
 * @date 2020/2/12 15:20
 */
@Slf4j
@Getter
public class FtpClientExt extends FTPClient {

    /**
     * 登录成功后的初始工作目录
     */
    private String defaultWorkingDir;

    /**
     * 初始化工作目录
     * @throws IOException
     */
    private void initDefaultWorkingDir() throws IOException{
        this.defaultWorkingDir = printWorkingDirectory();
    }

    @Override
    public boolean login(String username, String password) throws IOException {
        boolean isLogin = super.login(username, password);
        if (isLogin) {
            initDefaultWorkingDir();
            log.info("用户 [{}] 登录FTP服务成功，初始目录 [{}]", username, this.defaultWorkingDir);
        }
        return isLogin;
    }

    @Override
    public boolean login(String username, String password, String account) throws IOException {
        boolean isLogin = super.login(username, password, account);
        if (isLogin) {
            initDefaultWorkingDir();
            log.info("用户 [{}] 登录FTP服务成功，初始目录 [{}]", username, this.defaultWorkingDir);
        }
        return isLogin;
    }
}
