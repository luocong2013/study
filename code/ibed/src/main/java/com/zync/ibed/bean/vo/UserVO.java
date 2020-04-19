package com.zync.ibed.bean.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 用户视图对象
 * @date 2019/6/23 17:57
 */
@Data
@Builder
public class UserVO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 年龄
     */
    private Integer age;
}
