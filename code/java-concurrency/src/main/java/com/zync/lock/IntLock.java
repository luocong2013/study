package com.zync.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 中断响应
 *             线程在获取锁时，可以被中断
 *             如下产生死锁，其中一个线程被中断，让另一个线程可以执行完毕
 * @date 2019/8/4 15:58
 */
public class IntLock implements Runnable {

    private static ReentrantLock lock1 = new ReentrantLock();

    private static ReentrantLock lock2 = new ReentrantLock();

    int lock;

    /**
     * 控制加锁顺序，方便构造死锁
     * @param lock
     */
    public IntLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException ignored) {

                }
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException ignored) {

                }
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 检查当前线程是否持有该锁
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getName() + ": 线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntLock r1 = new IntLock(1);
        IntLock r2 = new IntLock(2);
        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r2, "t2");
        t1.start();
        t2.start();
        TimeUnit.MILLISECONDS.sleep(1000);
        // 中断其中一个线程
        t2.interrupt();
    }
}
