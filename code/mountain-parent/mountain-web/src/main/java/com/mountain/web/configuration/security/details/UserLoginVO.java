package com.mountain.web.configuration.security.details;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登录返回结果
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/15 15:46
 **/
@Getter
@Setter
@ToString
public class UserLoginVO {

    private String username;

    private String accessToken;

    private String refreshToken;
}
