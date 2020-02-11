package com.zync.pools.ftp.exception;

import com.zync.pools.ftp.client.FtpInfo;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption ftp连接创建异常
 * @date 2020/2/11 17:28
 */
public class FtpClientCreateException extends RuntimeException {

    private static final long serialVersionUID = 4977910517715958591L;

    private final transient FtpInfo ftpInfo;

    public FtpClientCreateException(FtpInfo ftpInfo) {
        this.ftpInfo = ftpInfo;
    }

    public FtpClientCreateException(FtpInfo ftpInfo, String message) {
        super(message);
        this.ftpInfo = ftpInfo;
    }

    public FtpClientCreateException(FtpInfo ftpInfo, String message, Throwable cause) {
        super(message, cause);
        this.ftpInfo = ftpInfo;
    }

    public FtpClientCreateException(FtpInfo ftpInfo, Throwable cause) {
        super(cause);
        this.ftpInfo = ftpInfo;
    }

    public FtpClientCreateException(FtpInfo ftpInfo, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.ftpInfo = ftpInfo;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return new StringBuilder("message: ").append(message).append("，ftpInfo: ").append(ftpInfo).toString();
    }
}
