package com.zync.pattern.singleton;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 懒汉模式（线程不安全）【不可用】
 * @date 2019/5/11 14:45
 */
public class Singleton3 {

    private static Singleton3 instance;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (instance == null) {
            instance = new Singleton3();
        }
        return instance;
    }
}
