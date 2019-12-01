package com.zync.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption CompletableFuture异步执行任务
 *             1. CompletableFuture.runAsync(Runnable runnable) 方法用于没有返回值的场景
 *             2. CompletableFuture.runAsync(Runnable runnable, Executor executor) 方法用于没有返回值的场景，并指定Runnable的工作线程池
 *             3. CompletableFuture.supplyAsync(Supplier<U> supplier) 方法用于有返回值的场景
 *             4. CompletableFuture.supplyAsync(Supplier<U> supplier, Executor executor) 方法用于有返回值的场景,并指定supplier的工作线程池
 * @date 2019/12/1 11:30
 */
public class CompletableFutureAsyncClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // supplyAsync() 函数会立即返回，不会阻塞
        final CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> calc(50))
                .thenApply(i -> Integer.toString(i))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        System.out.println("supplyAsync函数返回");
        long start = System.currentTimeMillis();
        System.out.println(future.get());
        System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");
    }

    public static Integer calc(Integer para) {
        try {
            // 模拟一个长时间的执行
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para * para;
    }
}
