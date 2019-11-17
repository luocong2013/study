package com.zync.future;

import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption jdk方式调用 Future
 * @date 2019/11/17 18:17
 */
public class JdkFutureClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 构造FutureTask
        //FutureTask<String> future = new FutureTask<>(new RealData("a"));
        //ExecutorService service = Executors.newFixedThreadPool(1);
        //service.submit(future);
        //System.out.println("请求完毕");
        //long start = System.currentTimeMillis();
        //try {
        //    TimeUnit.SECONDS.sleep(2);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        //System.out.println("数据 = " + future.get());
        //long end = System.currentTimeMillis();
        //System.out.println("耗时：" + (end - start) / 1000 + "秒");

        // 使用 Guava 的 ListenableFutureTask 异步处理
        ListenableFutureTask<String> future = ListenableFutureTask.create(new RealData("a"));
        future.addListener(() -> {
            System.out.println("异步处理成功");
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, MoreExecutors.directExecutor());
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(future);
        System.out.println("main is done");
    }
}
