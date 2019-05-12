package com.zync.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 模型
 * @date 2019/5/12 22:13
 */
public class RunnableDemo {

    private long count;

    private Lock lock = new ReentrantLock();

    public void incrementCount() {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
            System.out.println(count);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
