package com.zync.ibed.common;

import org.springframework.http.HttpStatus;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 同一结果集工厂方法
 * @date 2019/6/23 16:50
 */
public final class ResultApiFactory {

    private ResultApiFactory() {
    }

    /**
     * 封装处理成功数据
     * @param data     数据
     * @return
     */
    public static ResultApi buildSuccessResult(Object data) {
        return buildSuccessResult(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * 封装处理成功数据
     * @param code     状态码
     * @param message  消息
     * @param data     数据
     * @return
     */
    public static ResultApi buildSuccessResult(Integer code, String message, Object data) {
        return new ResultApi(code, message, Boolean.TRUE, data);
    }

    /**
     * 封装处理失败数据
     * @param code     状态码
     * @param message  消息
     * @param data     数据
     * @return
     */
    public static ResultApi buildFailResult(Integer code, String message, Object data) {
        return new ResultApi(code, message, Boolean.FALSE, data);
    }
}
