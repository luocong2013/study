package com.zync.swx.mongodb.annotation;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.mongodb.annotation
 * @description MongoDB 操作异常
 * @date 2017-12-5 15:47
 */
public class MongoDbException extends RuntimeException {

    public MongoDbException(){
    }

    public MongoDbException(String message) {
        super(message);
    }

    public MongoDbException(Throwable cause) {
        super(cause);
    }

    public MongoDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public MongoDbException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
