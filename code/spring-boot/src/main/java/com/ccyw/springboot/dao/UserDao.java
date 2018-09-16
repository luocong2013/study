package com.ccyw.springboot.dao;

import javax.annotation.PostConstruct;

/**
 * @author luoc
 * @version V1.0.0
 * @description TODO
 * @date 2018/7/11 23:20
 */
public class UserDao {

    public UserDao() {
        System.out.println("UserDao 构造函数 ......");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("UserDao PostConstruct ......");
    }

    public void init() {
        System.out.println("UserDao init ......");
    }

    public void say() {
        System.out.println("say ......");
    }

    public String hi() {
        return "hi";
    }
}
