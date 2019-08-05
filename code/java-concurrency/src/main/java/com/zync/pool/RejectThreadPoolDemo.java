package com.zync.pool;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 自定义线程池拒绝策略
 * @date 2019/8/5 21:16
 */
public class RejectThreadPoolDemo {

    public static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ": Thread Name: " + Thread.currentThread().getName());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 自定义线程池拒绝策略
     */
    public static class CustomExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(r.toString() + "is discard");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        ExecutorService service = new ThreadPoolExecutor(2, 2, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(4),
                new BasicThreadFactory.Builder().namingPattern("example-pool-%d").daemon(false).build(), new CustomExecutionHandler());

        for (int i = 0; i < 100; i++) {
            service.submit(task);
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }
}
