package com.zync.pattern.singleton;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 双重校验锁【推荐用】
 * @date 2019/5/11 14:52
 */
public class Singleton6 {

    private static volatile Singleton6 instance;

    private Singleton6() {
    }

    public static Singleton6 getInstance() {
        if (instance == null) {
            synchronized (Singleton6.class) {
                if (instance == null) {
                    instance = new Singleton6();
                }
            }
        }
        return instance;
    }
}
