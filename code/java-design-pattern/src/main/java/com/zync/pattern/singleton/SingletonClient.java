package com.zync.pattern.singleton;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 单例模式-测试方法
 * @date 2019/5/11 15:01
 */
public class SingletonClient {

    public static void main(String[] args){
        Singleton8.INSTANCE.say();
    }
}
