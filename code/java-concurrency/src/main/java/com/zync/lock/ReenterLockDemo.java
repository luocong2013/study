package com.zync.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 重入锁示例
 * @date 2019/8/4 15:50
 */
public class ReenterLockDemo implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();

    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            lock.lock();
            lock.lock();
            try {
                i++;
            } finally {
                // 加锁和释放锁的次数要一致
                lock.unlock();
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockDemo demo = new ReenterLockDemo();
        Thread t1 = new Thread(demo);
        Thread t2 = new Thread(demo);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
