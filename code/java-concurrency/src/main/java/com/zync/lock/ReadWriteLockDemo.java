package com.zync.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 读写锁
 * @date 2019/8/4 17:38
 */
public class ReadWriteLockDemo {

    private static Lock lock = new ReentrantLock();

    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static Lock readLock = readWriteLock.readLock();

    private static Lock writeLock = readWriteLock.writeLock();

    private int value;

    /**
     * 模拟读操作
     * @param lock
     * @return
     */
    public Object handleRead(Lock lock) {
        lock.lock();
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return value;
    }

    /**
     * 模拟写操作
     * @param lock
     * @param index
     */
    public void handleWrite(Lock lock, int index) {
        lock.lock();
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
            value = index;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Runnable readRunnable = () -> {
            // 这里使用读写分离，读线程完全并行，写线程会阻塞，整个执行时间大概3秒
            demo.handleRead(readLock);

            // 如果使用下面的代码，那么读写线程之间是串行的，整个执行时间大概20秒
            //demo.handleRead(lock);
        };

        Runnable writeRunnable = () -> {
            // 这里使用读写分离，读线程完全并行，写线程会阻塞，整个执行时间大概3秒
            demo.handleWrite(writeLock, new Random().nextInt());

            // 如果使用下面的代码，那么读写线程之间是串行的，整个执行时间大概20秒
            //demo.handleWrite(lock, new Random().nextInt());
        };

        List<Thread> threads = new ArrayList<>();
        Thread t;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 18; i++) {
            t = new Thread(readRunnable);
            t.start();
            threads.add(t);
        }

        for (int i = 18; i < 20; i++) {
            t = new Thread(writeRunnable);
            t.start();
            threads.add(t);
        }

        for (Thread th : threads) {
            th.join();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");
    }
}
