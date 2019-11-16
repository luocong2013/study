package com.zync.pattern.singleton;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 饿汉模式（静态常量）
 * @date 2019/11/16 17:06
 */
public class Singleton1_1 {

    public static int STATUS = 1;

    private Singleton1_1() {
        System.out.println("创建单例");
    }

    private static Singleton1_1 instance = new Singleton1_1();

    public static Singleton1_1 getInstance() {
        return instance;
    }
}
