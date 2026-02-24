package com.mountain.web.basic.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 授权类型枚举
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/24 14:06
 **/
@Getter
@AllArgsConstructor
public enum AuthorizeTypeEnum implements IEnum<Integer> {
    /**
     * 角色授权
     */
    ROLE_AUTHORIZE(0),
    /**
     * 跟随父资源授权
     */
    PARENT_AUTHORIZE(1),
    /**
     * 登录授权，登录用户即可访问
     */
    LOGIN_AUTHORIZE(2)
    ;

    private final Integer value;
}
