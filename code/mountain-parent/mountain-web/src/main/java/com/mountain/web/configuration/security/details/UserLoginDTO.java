package com.mountain.web.configuration.security.details;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登录DTO
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/10 14:22
 **/
@Getter
@Setter
@ToString
public class UserLoginDTO {

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录密码（rsa加密传输）
     */
    private String password;
}
