package com.zync.security.session.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption
 * @date 2020/7/11 14:47
 */
@Data
@Builder
public class UserDTO {

    /**
     * session存user的key
     */
    public static final String SESSION_USER_KEY = "_user";

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 全名
     */
    private String fullname;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 用户权限
     */
    private Set<String> authorities;
}
