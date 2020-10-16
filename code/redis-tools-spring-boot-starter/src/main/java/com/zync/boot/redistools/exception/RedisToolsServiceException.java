package com.zync.boot.redistools.exception;

/**
 * @author luocong
 * @version V1.0.0
 * @descrption redis工具服务端异常
 * @date 2020/9/19 15:58
 */
public class RedisToolsServiceException extends RuntimeException {

    private static final long serialVersionUID = -315915460165567087L;

    public RedisToolsServiceException() {
        super();
    }

    public RedisToolsServiceException(String message) {
        super(message);
    }

    public RedisToolsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisToolsServiceException(Throwable cause) {
        super(cause);
    }

    protected RedisToolsServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
