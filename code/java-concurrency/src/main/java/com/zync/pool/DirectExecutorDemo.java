package com.zync.pool;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Guava MoreExecutors 的一些使用
 * @date 2019/10/27 23:36
 */
public class DirectExecutorDemo {

    public static void main(String[] args){
        // 直接在主线程中执行任务
        Executor executor = MoreExecutors.directExecutor();
        executor.execute(() -> System.out.println("I am running in " + Thread.currentThread().getName()));

        // 转换普通线程池为 Daemon 线程池
        ThreadPoolExecutor executor1 = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        MoreExecutors.getExitingExecutorService(executor1);
        executor1.execute(() -> System.out.println("I am running in " + Thread.currentThread().getName()));
    }
}
