package com.zync.concurrent;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch 并发测试
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/8/16 17:05
 */
public class ConcurrentTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        int threadCount = 10;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(threadCount);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(new Worker(i, startSignal, doneSignal));
        }

        System.out.println("Press Enter to start...");
        // 按回车键开始
        System.in.read();

        long startTime = System.currentTimeMillis();

        // 开始任务
        startSignal.countDown();
        // 等待所有任务完成
        doneSignal.await();

        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (endTime - startTime) + " ms");
        executorService.shutdown();
    }

    private static class Worker implements Runnable {

        private final int id;
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        public Worker(int id, CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.id = id;
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                System.out.println("Worker " + id + " await.");
                startSignal.await();
                doWork();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                doneSignal.countDown();
            }
        }

        private void doWork() {
            // 模拟耗时工作，例如 HTTP 请求
            System.out.println("Worker " + id + " is doing work.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
