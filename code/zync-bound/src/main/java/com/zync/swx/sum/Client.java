package com.zync.swx.sum;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.sum
 * @description TODO
 * @date 2017-10-24 16:09
 */
public class Client {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(0, 100L);
        //同步执行
        long sum = pool.invoke(task);

        //调用get()方法时会阻塞线程，即同步执行 否则异步执行
//        long sum = pool.submit(task).get();
        pool.shutdown();
        System.out.println(sum);
        System.out.println((System.currentTimeMillis()-start)+"ms");
    }

}
