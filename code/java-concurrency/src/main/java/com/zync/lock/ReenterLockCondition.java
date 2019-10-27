package com.zync.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 重入锁：Condition条件
 *             await() 使当前线程等待，同时释放当前锁
 *             awaitUninterruptibly() 与 await() 方法类似，但是它不会在等待过程中响应中断
 *             signal() 用于唤醒一个在等待中的线程
 *             signalAll() 唤醒所有在等待中的线程
 * @date 2019/8/4 17:01
 */
public class ReenterLockCondition implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println(System.currentTimeMillis() + ": 线程进入！");
            condition.await();
            System.out.println(System.currentTimeMillis() + ": Thread is going on.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockCondition r1 = new ReenterLockCondition();
        Thread t1 = new Thread(r1);
        t1.start();
        TimeUnit.MILLISECONDS.sleep(2000);

        // 通知线程t1继续执行
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
