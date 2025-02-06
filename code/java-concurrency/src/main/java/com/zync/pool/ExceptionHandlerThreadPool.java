package com.zync.pool;

import java.util.concurrent.*;

/**
 * @author admin
 * @version 1.0
 * @description 线程池异常处理
 * @since 2025/2/6 10:25
 **/
public class ExceptionHandlerThreadPool {

    public static void main(String[] args) throws InterruptedException {

        ThreadFactory factory = r -> {
            Thread t = new Thread(r);
            // 给创建的线程设置UncaughtExceptionHandler对象 里面实现异常的处理逻辑
            t.setUncaughtExceptionHandler((t1, e) -> {
                // 此方法是由JVM调用 @see java.lang.Thread.dispatchUncaughtException
                System.out.println("线程工厂设置的exceptionHandler: " + e.getMessage());
            });
            return t;
        };

        ExecutorService executorService = new ThreadPoolExecutor(2, 4,
                10, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(10), factory);

        // submit 无提示
        executorService.submit(new Task());

        Thread.sleep(1000);

        System.out.println("==================为检验打印结果，1秒后执行execute方法");

        //execute 方法被线程工厂 factory 的 UncaughtExceptionHandler 捕捉到异常
        executorService.execute(new Task());

        executorService.shutdown();
    }



    public static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println("进入了task的run方法！");
            int i = 1 / 0;
        }
    }
}
