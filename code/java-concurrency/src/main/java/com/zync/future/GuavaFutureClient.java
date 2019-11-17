package com.zync.future;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Future.get()方法得到结果是阻塞的
 *             Guava中，增强了Future模式，增加了对Future模式完成时的回调接口
 * @date 2019/11/17 18:24
 */
public class GuavaFutureClient {

    public static void main(String[] args) throws InterruptedException {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<String> task = service.submit(new RealData("b"));
        task.addListener(() -> {
            System.out.println("异步处理成功");
            try {
                System.out.println(task.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, MoreExecutors.directExecutor());

        // 增加异常处理功能
        Futures.addCallback(task, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("异步处理成功，result = " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("异步处理失败，e = " + t);
            }
        }, MoreExecutors.newDirectExecutorService());
        System.out.println("main task done ......");
    }
}
