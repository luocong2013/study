package com.zync.ibed.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 同一返回结果集
 * @date 2019/6/23 16:45
 */
@Data
@AllArgsConstructor
public class ResultApi<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 数据
     */
    private T data;

}
