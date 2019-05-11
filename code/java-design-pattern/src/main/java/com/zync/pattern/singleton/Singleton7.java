package com.zync.pattern.singleton;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 静态内部类【推荐用】
 * @date 2019/5/11 14:54
 */
public class Singleton7 {

    private Singleton7() {
    }

    private static class SingletonHolder {
        private static final Singleton7 INSTANCE = new Singleton7();
    }

    public static Singleton7 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
