package com.ccyw.springboot.dao;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author luoc
 * @version V1.0.0
 * @description TODO
 * @date 2018/7/11 23:22
 */
public class BookDao {

    @Autowired
    private UserDao userDao;

    public BookDao() {
        System.out.println("BookDao 构造函数 ......");
        System.out.println("BookDao userDao == null " + (userDao == null));
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("BookDao PostConstruct ......");
        System.out.println("BookDao userDao.hi() " + userDao.hi());
    }

    public void init() {
        System.out.println("BookDao init ......");
        System.out.println("BookDao userDao.hi() " + userDao.hi());
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("BookDao PreDestroy ......");
        System.out.println("BookDao userDao.hi() " + userDao.hi());
    }

    public void destroy() {
        System.out.println("BookDao destroy ......");
        System.out.println("BookDao userDao.hi() " + userDao.hi());
    }

    public void getBook() {
        System.out.println("BookDao get book ......");
    }
}
