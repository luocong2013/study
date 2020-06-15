package com.zync.swx.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author LC
 * @version V1.0.0
 * @date 2018-1-19 16:06
 */
public class ExecutorRunnableTest {

    static class Runner implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is called");
        }
    }

    public static void main(String[] args){
        ThreadFactory factory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService service = new ThreadPoolExecutor(1, 4, 60L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(20), factory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 20; i++) {
            service.execute(new Runner());
        }
        service.shutdown();
    }
}
