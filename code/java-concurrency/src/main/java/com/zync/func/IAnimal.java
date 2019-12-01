package com.zync.func;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption
 * @date 2019/11/26 21:41
 */
public interface IAnimal {

    default void breath() {
        System.out.println("breath");
    }
}
