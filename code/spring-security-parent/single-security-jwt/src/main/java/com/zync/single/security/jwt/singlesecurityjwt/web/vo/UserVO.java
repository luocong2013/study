package com.zync.single.security.jwt.singlesecurityjwt.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户视图类
 * @date 2020/8/28 18:25
 */
@Data
@AllArgsConstructor
public class UserVO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
}
