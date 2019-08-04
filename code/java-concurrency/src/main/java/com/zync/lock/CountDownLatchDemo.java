package com.zync.lock;

import com.zync.pool.ThreadPoolHelper;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 倒计时器：CountDownLatch
 * @date 2019/8/4 18:07
 */
public class CountDownLatchDemo implements Runnable {

    private static CountDownLatch end = new CountDownLatch(10);

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10) * 1000);
            System.out.println("check complete");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            end.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo demo = new CountDownLatchDemo();
        for (int i = 0; i < 10; i++) {
            ThreadPoolHelper.executeTask(demo);
        }
        // 等待
        end.await();
        System.out.println("Fire!");
        ThreadPoolHelper.shutdown();
    }
}
