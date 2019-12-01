package com.zync.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption CompletableFuture异常处理
 * @date 2019/12/1 11:56
 */
public class CompletableFutureExceptionClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> calc(50))
                .exceptionally(e -> {
                    System.out.println(e.toString());
                    return 0;
                })
                .thenApply(i -> Integer.toString(i))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        future.get();
    }

    public static Integer calc(Integer para) {
        return para / 0;
    }
}
