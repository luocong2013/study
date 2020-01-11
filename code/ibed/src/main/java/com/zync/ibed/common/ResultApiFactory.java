package com.zync.ibed.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 统一结果集工厂方法
 * @date 2019/6/23 16:50
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResultApiFactory {

    /**
     * 封装处理成功数据
     * @param data     数据
     * @return
     */
    public static <T> ResultApi buildSuccessResult(T data) {
        return buildSuccessResult(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * 封装处理成功数据
     * @param code     状态码
     * @param message  消息
     * @param data     数据
     * @return
     */
    public static <T> ResultApi buildSuccessResult(Integer code, String message, T data) {
        return new ResultApi<>(code, message, Boolean.TRUE, data);
    }

    /**
     * 封装处理失败数据
     * @param data 数据
     * @return
     */
    public static <T> ResultApi buildFailResult(T data) {
        return buildFailResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), data);
    }

    /**
     * 封装处理失败数据
     * @param code     状态码
     * @param message  消息
     * @param data     数据
     * @return
     */
    public static <T> ResultApi buildFailResult(Integer code, String message, T data) {
        return new ResultApi<>(code, message, Boolean.FALSE, data);
    }
}
