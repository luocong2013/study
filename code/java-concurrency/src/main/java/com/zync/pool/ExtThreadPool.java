package com.zync.pool;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 扩展线程池
 * @date 2019/8/5 21:26
 */
public class ExtThreadPool {

    public static class Task implements Runnable {

        private String name;

        public Task(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ": Thread Name: " + Thread.currentThread().getName());
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 自定义线程池
     */
    public static class CustomThreadPoolExecutor extends ThreadPoolExecutor {

        public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("准备执行：" + ((Task) r).getName());
            System.out.println("线程池中线程数：" + this.getPoolSize());
            System.out.println("队列中等待执行的任务数：" + this.getQueue().size());
            System.out.println("已执行完任务数：" + this.getCompletedTaskCount());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println("执行完成：" + ((Task) r).getName());
        }

        @Override
        protected void terminated() {
            System.out.println("线程池退出");
        }
    }

    public static void main(String[] args) {
        ExecutorService service = new CustomThreadPoolExecutor(2, 4, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20),
                new BasicThreadFactory.Builder().namingPattern("example-pool-%d").daemon(false).build(), new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 24; i++) {
            Task task = new Task("TASK-GEYM-" + i);
            service.execute(task);
        }

        service.shutdown();
    }
}
