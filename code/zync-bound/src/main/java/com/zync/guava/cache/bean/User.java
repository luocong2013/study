package com.zync.guava.cache.bean;

import lombok.Data;

/**
 * @author luocong
 * @description 用户对象
 * @date 2020/5/21 9:56
 */
@Data
public class User {

    private String username;

    private String password;

    private String address;

    private Long phone;
}
