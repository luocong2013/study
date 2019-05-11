package com.zync.pattern.singleton;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 饿汉模式（静态常量）【可用】
 * @date 2019/5/11 14:41
 */
public class Singleton1 {

    private static final Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return INSTANCE;
    }
}
