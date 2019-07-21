package com.zync.common;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 返回结果
 * @date 2019/7/21 11:22
 */
public final class Result<T> {

    private Integer code;

    private Boolean success;

    private String message;

    private T data;

    public static <T> Result buildSuccessResult(Integer code, String message, T data) {
        return new Result(code, true, message, data);
    }

    private Result(Integer code, Boolean success, String message, T data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
