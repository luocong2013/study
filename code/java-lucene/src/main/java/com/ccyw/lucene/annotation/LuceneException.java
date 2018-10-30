package com.ccyw.lucene.annotation;

/**
 * @author luoc
 * @version V1.0.0
 * @description Lucene操作异常
 * @date 2018/10/27 22:50
 */
public class LuceneException extends RuntimeException {
    public LuceneException() {
    }

    public LuceneException(String message) {
        super(message);
    }

    public LuceneException(String message, Throwable cause) {
        super(message, cause);
    }

    public LuceneException(Throwable cause) {
        super(cause);
    }

    public LuceneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
