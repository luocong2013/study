package com.zync.lock;

import com.zync.pool.ThreadPoolHelper;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 死锁程序
 * @date 2019/7/21 17:28
 */
public class DeadLock {

    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        ThreadPoolHelper.executeTask(() -> {
            while (true) {
                synchronized (lock1) {
                    synchronized (lock2) {
                        System.out.println("Thread1");
                    }
                }
            }
        });

        ThreadPoolHelper.executeTask(() -> {
            while (true) {
                synchronized (lock2) {
                    synchronized (lock1) {
                        System.out.println("Thread2");
                    }
                }
            }
        });
    }
}
