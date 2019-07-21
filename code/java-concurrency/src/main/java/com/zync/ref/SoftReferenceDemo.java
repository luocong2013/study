package com.zync.ref;

import com.zync.common.User;

import java.lang.ref.SoftReference;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 软引用，在JVM内存充足的情况下，软引用并不会被垃圾回收器回收，只有在JVM内存不足的情况下，才会被垃圾回收器回收
 * @date 2019/7/21 14:31
 */
public class SoftReferenceDemo {

    public static void main(String[] args){
        SoftReference<User> softReference = new SoftReference<>(new User("张三", "123", 20));
        System.out.println(softReference.get());
    }
}
