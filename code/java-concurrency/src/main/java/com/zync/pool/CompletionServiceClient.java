package com.zync.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author luocong
 * @version V1.0.0
 * @description CompletionService  内部通过阻塞队列+FutureTask实现 任务完成就可获取到，即结果按照完成先后顺序输出，与放入线程池的顺序无关
 * @date 2020/6/3 10:55
 */
public class CompletionServiceClient {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService, new LinkedBlockingQueue<>(100));

        long start = System.currentTimeMillis();
        List<Future<String>> futureList = new ArrayList<>();
        try {
            int count = 5;
            for (int i = count; i > 0; i--) {
                futureList.add(completionService.submit(new Task(i)));
            }
            for (int i = 0; i < 5; i++) {
                try {
                    // 检索并移除下一个已完成任务的 Future，如果目前不存在这样的任务，则等待
                    Future<String> future = completionService.take();
                    System.out.println(future.get() + " 执行完成");
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            for (Future<String> future : futureList) {
                future.cancel(true);
            }
        }

        System.out.println("总耗时：" + (System.currentTimeMillis() - start) + " ms");
        executorService.shutdown();
    }

    private static class Task implements Callable<String> {

        private final int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public String call() throws Exception {
            TimeUnit.MILLISECONDS.sleep(i * 500L);
            return "任务：" + i;
        }
    }
}
