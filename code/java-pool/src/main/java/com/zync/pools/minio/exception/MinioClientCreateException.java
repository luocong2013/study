package com.zync.pools.minio.exception;

import com.zync.pools.minio.client.MinioInfo;

/**
 * MinioClientCreateException
 *
 * @author luocong
 * @version V1.0.0
 * @description minio client 创建异常
 * @date 2020年02月13日 14:13
 */
public class MinioClientCreateException extends RuntimeException {

    private static final long serialVersionUID = 5890940532137673562L;

    private final transient MinioInfo minioInfo;

    public MinioClientCreateException(MinioInfo minioInfo) {
        this.minioInfo = minioInfo;
    }

    public MinioClientCreateException(MinioInfo minioInfo, String message) {
        super(message);
        this.minioInfo = minioInfo;
    }

    public MinioClientCreateException(MinioInfo minioInfo, String message, Throwable cause) {
        super(message, cause);
        this.minioInfo = minioInfo;
    }

    public MinioClientCreateException(MinioInfo minioInfo, Throwable cause) {
        super(cause);
        this.minioInfo = minioInfo;
    }

    public MinioClientCreateException(MinioInfo minioInfo, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.minioInfo = minioInfo;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return new StringBuilder("message: ").append(message).append("，minioInfo: ").append(minioInfo).toString();
    }
}
