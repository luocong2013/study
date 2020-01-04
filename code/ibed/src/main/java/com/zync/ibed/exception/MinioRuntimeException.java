package com.zync.ibed.exception;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Minio运行异常
 * @date 2020/1/4 15:43
 */
public class MinioRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 5012620802211767641L;

    public MinioRuntimeException() {
        super();
    }

    public MinioRuntimeException(String message) {
        super(message);
    }

    public MinioRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinioRuntimeException(Throwable cause) {
        super(cause);
    }

    protected MinioRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
