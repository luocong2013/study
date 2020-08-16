package com.zync.distributed.security.uaa.model;

import lombok.Data;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption user实体
 * @date 2020/7/19 14:39
 */
@Data
public class UserDTO {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户名
     */
    private String fullname;
    /**
     * 电话
     */
    private String mobile;
}
