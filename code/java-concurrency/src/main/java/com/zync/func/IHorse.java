package com.zync.func;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption
 * @date 2019/11/26 21:39
 */
public interface IHorse {

    void eat();

    default void run() {
        System.out.println("hourse run");
    }
}
