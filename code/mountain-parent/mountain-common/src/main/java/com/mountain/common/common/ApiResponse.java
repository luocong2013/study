package com.mountain.common.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * api 响应体
 *
 * @author luocong
 * @version 1.0
 * @since 2026/1/15 14:10
 **/
@Getter
@Setter
@Builder
public class ApiResponse<T> {

    /**
     * 状态码
     */
    private int code;
    /**
     * 描述信息（用户）
     */
    private String message;
    /**
     * 简要描述信息（开发者）
     */
    private String developerMessage;
    /**
     * 更多详细信息
     */
    private String moreInfo;
    /**
     * 数据
     */
    private T data;
}
