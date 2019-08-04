package com.zync.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 非公平锁
 * @date 2019/8/4 16:17
 */
public class FairLock implements Runnable {

    private static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + ": 获得锁！");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock r1 = new FairLock();
        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r1, "t2");
        t1.start();
        t2.start();
    }
}
