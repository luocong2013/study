package com.zync.security.session.model;

import lombok.Data;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户认证请求，账号、密码
 * @date 2020/7/11 14:46
 */
@Data
public class AuthenticationRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
