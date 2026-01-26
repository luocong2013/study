package com.mountain.common.exception;

import com.mountain.common.base.Const;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * 业务异常
 *
 * @author luocong
 * @version v1.0
 * @since 2023/11/1 10:53
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

    public BizException(HttpStatusCode status, String message, String developerMessage) {
        super(message);
        this.status = status;
        this.developerMessage = developerMessage;
    }

    public BizException(HttpStatusCode status, String message, String developerMessage, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.developerMessage = developerMessage;
    }

    public BizException(Throwable cause) {
        super(cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.developerMessage = Const.GLOBAL_EXCEPTION_MESSAGE;
    }

    public BizException(String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public BizException(HttpStatusCode status, String message) {
        this(status, message, message);
    }

    public BizException(String message, Throwable cause) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }

    public BizException(HttpStatusCode status, String message, Throwable cause) {
        this(status, message, message, cause);
    }

}
