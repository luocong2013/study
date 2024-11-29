package com.customzied.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * User Defined Exception
 *
 * @author luocong
 * @version v1.0
 * @date 2023/11/1 10:53
 */
@Getter
public class BizException extends RuntimeException {

    /**
     * 状态码
     */
    private final HttpStatusCode status;
    /**
     * 简要描述信息（开发者）
     */
    private final String developerMessage;

    public BizException(String message, HttpStatusCode status, String developerMessage) {
        super(message);
        this.status = status;
        this.developerMessage = developerMessage;
    }

    public BizException(String message, HttpStatusCode status, String developerMessage, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.developerMessage = developerMessage;
    }


    public BizException(String message, HttpStatusCode status) {
        this(message, status, message);
    }

    public BizException(String message, HttpStatusCode status, Throwable cause) {
        this(message, status, message, cause);
    }

    public BizException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public BizException(String message, Throwable cause) {
        this(message, HttpStatus.BAD_REQUEST, cause);
    }

}
