package com.zync.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption CompletableFuture组合多个CompletableFuture
 *             1. 使用 thenCompose(Function<? super T, ? extends CompletionStage<U>> fn)
 *             2. 使用 thenCombine(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)
 *                thenCombine首先完成当前CompletableFuture和other的执行。
 *                接着，将这两者的执行结果传递给BiFunction（该接口接收两个参数，并有一个返回值），并代表BiFunction实例的CompletableFuture对象
 * @date 2019/12/1 12:01
 */
public class CompletableFutureComposeClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> calc(50))
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> calc(i)))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        future.get();

        final CompletableFuture<Void> completableFuture = CompletableFuture
                .supplyAsync(() -> calc(50))
                .thenCombine(CompletableFuture.supplyAsync(() -> calc(25)), Integer::sum)
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        completableFuture.get();
    }

    public static Integer calc(Integer para) {
        return para / 2;
    }

}
