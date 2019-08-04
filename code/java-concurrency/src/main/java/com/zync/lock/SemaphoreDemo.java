package com.zync.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 信号量
 * @date 2019/8/4 17:27
 */
public class SemaphoreDemo implements Runnable {

    private static final Semaphore SEMAPHORE = new Semaphore(5);

    @Override
    public void run() {
        try {
            SEMAPHORE.acquire();
            // 模拟耗时
            TimeUnit.MILLISECONDS.sleep(2000);
            System.out.println(Thread.currentThread().getName() + ": done!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            SEMAPHORE.release();
        }
    }

    public static void main(String[] args){
        ExecutorService service = Executors.newFixedThreadPool(20);
        final SemaphoreDemo demo = new SemaphoreDemo();
        for (int i = 0; i < 20; i++) {
            service.execute(demo);
        }
    }
}
