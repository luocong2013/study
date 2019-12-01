package com.zync.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption CompletableFuture支持timeout
 *             JDK 9 以后CompletableFuture增加了timeout功能。
 *             如果一个任务在给定的时间内没有完成，则直接抛出异常
 * @date 2019/12/1 16:53
 */
public class CompletableFutureTimeoutClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return calc(50);
        })/*.orTimeout(1, TimeUnit.SECONDS)*/.exceptionally(e -> {
            System.out.println(e.toString());
            return 0;
        }).thenAccept(System.out::println).get();
    }

    public static Integer calc(Integer para) {
        return para / 2;
    }
}
