package com.zync.pool;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 除法线程池
 * @date 2019/8/5 21:51
 */
public class DivThreadPool {

    public static class DivTask implements Runnable {

        private int a;

        private int b;

        public DivTask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            double re = a / b;
            System.out.println(re);
        }
    }

    public static class TraceThreadPoolExecutor extends ThreadPoolExecutor {

        public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        public void execute(Runnable task) {
            super.execute(wrap(task, clientTrace(), Thread.currentThread().getName()));
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
        }

        private Exception clientTrace() {
            return new Exception("Client stack trace");
        }

        private Runnable wrap(final Runnable task, final Exception clientStack, String clientThreadName) {
            return () -> {
                try {
                    task.run();
                } catch (Exception e) {
                    clientStack.printStackTrace();
                    throw e;
                }
            };
        }
    }

    public static void main(String[] args){
        /*ExecutorService service = new ThreadPoolExecutor(2, 4, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20),
                new BasicThreadFactory.Builder().namingPattern("example-pool-%d").daemon(false).build(), new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 5; i++) {
            // ① 使用submit没有任何异常线程，因为 submit 的runnable会被包装为FutureTask，FutureTask的run方法会捕获Throwable set到outcome里，可以通过 get 获取异常
            service.submit(new DivTask(100, i));

            // ② 使用execute有异常信息
            //service.execute(new DivTask(100, i));
        }*/


        // 使用自定义异常信息截取线程池
        ExecutorService service = new TraceThreadPoolExecutor(2, 4, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20),
                new BasicThreadFactory.Builder().namingPattern("example-pool-%d").daemon(false).build(), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 5; i++) {
            //service.submit(new DivTask(100, i));

            // 异常信息更详细
            service.execute(new DivTask(100, i));
        }
    }
}
