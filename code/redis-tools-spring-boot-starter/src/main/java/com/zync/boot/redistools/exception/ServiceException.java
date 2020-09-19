package com.zync.boot.redistools.exception;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 服务端异常
 * @date 2020/9/19 15:58
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -315915460165567087L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
