package com.zync.func;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption
 * @date 2019/11/26 21:43
 */
public interface IDonkey {

    void eat();

    default void run() {
        System.out.println("Donkey run");
    }
}
