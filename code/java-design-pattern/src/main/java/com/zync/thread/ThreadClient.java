package com.zync.thread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 测试方法
 * @date 2019/5/12 22:07
 */
public class ThreadClient {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = new ThreadPoolExecutor(10, 16, 20, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000), new BasicThreadFactory.Builder().namingPattern("example-%d").daemon(false).build());
        RunnableDemo runnableDemo = new RunnableDemo();
        for (int i = 0; i < 10; i++) {
            executorService.execute(new RunnableTask(runnableDemo));
        }
    }
}
