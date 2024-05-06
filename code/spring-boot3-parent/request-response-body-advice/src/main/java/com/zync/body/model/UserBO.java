package com.zync.body.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户BO
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/29 18:48
 */
@Getter
@Setter
@ToString
public class UserBO {

    /**
     * 姓名
     */
    private String name;
    /**
     * 验证码
     */
    private Integer code;

}
