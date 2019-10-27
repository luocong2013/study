package com.zync.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 锁申请等待限时 lock.tryLock(long timeout, TimeUnit unit)
 * @date 2019/10/27 19:31
 */
public class TimeLock implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " get lock success");
                TimeUnit.SECONDS.sleep(6);
            } else {
                System.out.println(Thread.currentThread().getName() + " get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args){
        TimeLock timeLock = new TimeLock();
        Thread t1 = new Thread(timeLock, "t1");
        Thread t2 = new Thread(timeLock, "t2");
        t1.start();
        t2.start();
    }
}
