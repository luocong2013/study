package com.zync.ftpserver.ftpserverminimal.exception;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption ftp server start exception
 * @date 2020/3/10 21:05
 */
public class FtpserverStartFailedException extends RuntimeException {
    private static final long serialVersionUID = 162790905448937980L;

    public FtpserverStartFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
