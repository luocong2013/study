package com.zync.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption CompletableFuture和Future一样，可以作为函数调用的契约
 * @date 2019/12/1 11:18
 */
public class CompletableFutureClient {

    public static class AskThread implements Runnable {

        CompletableFuture<Integer> future = null;

        public AskThread(CompletableFuture<Integer> future) {
            this.future = future;
        }

        @Override
        public void run() {
            int myRe = 0;
            long start = System.currentTimeMillis();
            try {
                myRe = future.get() * future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(myRe);
            System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(new AskThread(future)).start();
        // 模拟长时间的计算过程
        TimeUnit.SECONDS.sleep(2);
        // 告知完成结果
        future.complete(60);
    }
}
