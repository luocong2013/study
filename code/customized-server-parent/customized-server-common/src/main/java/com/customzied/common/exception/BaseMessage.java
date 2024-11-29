package com.customzied.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * base rest response
 *
 * @author luocong
 * @version v1.0
 * @date 2023/11/1 11:02
 */
@Getter
@Setter
@Builder
public class BaseMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -9143234037177972208L;

    /**
     * 状态码
     */
    private int code;
    /**
     * 描述信息（用户）
     */
    private String userMessage;
    /**
     * 简要描述信息（开发者）
     */
    private String developerMessage;
    /**
     * 更多详细信息
     */
    private String moreInfo;

}
