package com.zync.swx.demo;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.demo
 * @description TODO
 * @date 2017-10-23 18:08
 */
public class Main {

    public static void main(String[] args) {
        ProductListGenerator generator = ProductListGenerator.getInstance();
        List<Product> products = generator.generate(1000000);
        Task task = new Task(products, 0, products.size(), 0.2);
        ForkJoinPool pool = new ForkJoinPool();
        //异步执行
        //pool.execute(task);

        //同步执行
        pool.invoke(task);

        /*try {
            //调用get()方法时会阻塞线程，即同步执行 否则异步执行
            pool.submit(task).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        /*do {
            System.out.printf("Main: Thread Count: %d\n", pool.getActiveThreadCount());
            System.out.printf("Main: Thread Steal: %d\n", pool.getStealCount());
            System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
            try {
                TimeUnit.MICROSECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());*/

//        pool.awaitQuiescence(0, TimeUnit.SECONDS);
        //关闭线程池
        pool.shutdown();

        if (task.isCompletedNormally()) {
            System.out.printf("Main: The process has completed normally.\n");
        }

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getPrice() != 12) {
                System.out.printf("Product %s: %f\n", product.getName(), product.getPrice());
            }
        }

        System.out.println("Main: End of the program.\n");
    }
}
