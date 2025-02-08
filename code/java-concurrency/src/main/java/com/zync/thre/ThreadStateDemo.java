package com.zync.thre;

import java.util.concurrent.locks.LockSupport;

/**
 * @author admin
 * @version 1.0
 * @description 测试线程状态
 * @since 2025/2/7 14:20
 **/
public class ThreadStateDemo {

    private static final Object object = new Object();

    public static void main(String[] args) {
        Runnable runnable = () -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + ": 线程进入");
                try {
                    // 以下线程1状态均为: WAITING
                    // object.wait();
                    // Thread.currentThread().join();
                    // LockSupport.park();

                    // 以下线程1状态均为: TIMED_WAITING
                    // Thread.sleep(99999999);
                    // object.wait(99999999);
                    // Thread.currentThread().join(99999999);
                    LockSupport.parkNanos(99999999999999999L);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread t1 = new Thread(runnable, "t1");
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread t2 = new Thread(runnable, "t2");
        t2.start();


        System.out.println("线程1状态: " + t1.getState());
        System.out.println("线程2状态: " + t2.getState());
    }
}
