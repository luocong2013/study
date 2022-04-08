package com.zync.boot.ftppool.exception;

import com.zync.boot.ftppool.client.FtpClientProperties;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption ftp连接创建异常
 * @date 2020/2/12 15:37
 */
public class FtpClientCreateException extends RuntimeException {

    private static final long serialVersionUID = 4977910517715958591L;

    private final transient FtpClientProperties properties;

    public FtpClientCreateException(FtpClientProperties properties) {
        this.properties = properties;
    }

    public FtpClientCreateException(FtpClientProperties properties, String message) {
        super(message);
        this.properties = properties;
    }

    public FtpClientCreateException(FtpClientProperties properties, String message, Throwable cause) {
        super(message, cause);
        this.properties = properties;
    }

    public FtpClientCreateException(FtpClientProperties properties, Throwable cause) {
        super(cause);
        this.properties = properties;
    }

    public FtpClientCreateException(FtpClientProperties properties, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.properties = properties;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return new StringBuilder("message: ").append(message).append("，ftpInfo: ").append(properties).toString();
    }
}
