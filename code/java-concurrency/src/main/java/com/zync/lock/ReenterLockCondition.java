package com.zync.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 重入锁：Condition条件
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
