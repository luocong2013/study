package com.zync.body.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户VO
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/29 18:49
 */
@Getter
@Setter
@ToString
public class UserVO {

    /**
     * 姓名
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 年龄
     */
    private Integer age;

    public UserVO() {
    }

    public UserVO(String name, String address, Integer age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }
}
