package com.customzied.common.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

/**
 * 自定义api响应http状态码
 *
 * @author luocong
 * @version 1.0
 * @since 2026/1/16 9:22
 **/
@Getter
@AllArgsConstructor
public enum ApiHttpStatus {

    /**
     * 自定义api响应http状态码
     */
    PAY_ERROR(HttpStatusCode.valueOf(10000)),
    ;

    private final HttpStatusCode status;

}
