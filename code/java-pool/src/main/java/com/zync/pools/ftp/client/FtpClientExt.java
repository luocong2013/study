package com.zync.pools.ftp.client;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 继承 org.apache.commons.net.ftp.FTPClient
 *             增加defaultWorkingDir，便于使用ftp client连接池时，支持复位
 * @date 2020/2/11 18:15
 */
@Getter
@Setter
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
        System.out.println("login....");
    }

    @Override
    public boolean login(String username, String password) throws IOException {
        boolean isLogin = super.login(username, password);
        if (isLogin) {
            initDefaultWorkingDir();
        }
        return isLogin;
    }

    @Override
    public boolean login(String username, String password, String account) throws IOException {
        boolean isLogin = super.login(username, password, account);
        if (isLogin) {
            initDefaultWorkingDir();
        }
        return isLogin;
    }
}
