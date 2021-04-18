package com.zync.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 偏向锁撤销
 * @date 2021/4/18 15:18
 */
public class TestBiasedCancel {

    public static void main(String[] args){
        Dog dog = new Dog();
        new Thread(() -> {
            System.out.println(ClassLayout.parseInstance(dog).toPrintable());
            synchronized (dog) {
                System.out.println(ClassLayout.parseInstance(dog).toPrintable());
            }
            System.out.println(ClassLayout.parseInstance(dog).toPrintable());

            synchronized (TestBiasedCancel.class) {
                TestBiasedCancel.class.notify();
            }
        }, "t1").start();


        new Thread(() -> {

            synchronized (TestBiasedCancel.class) {
                try {
                    TestBiasedCancel.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(ClassLayout.parseInstance(dog).toPrintable());
            synchronized (dog) {
                System.out.println(ClassLayout.parseInstance(dog).toPrintable());
            }
            System.out.println(ClassLayout.parseInstance(dog).toPrintable());
        }, "t2").start();
    }
}
