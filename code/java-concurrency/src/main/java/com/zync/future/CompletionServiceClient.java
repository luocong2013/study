package com.zync.future;

import java.util.concurrent.*;

/**
 * @author luocong
 * @version V1.0.0
 * @description CompletionService  内部通过阻塞队列+FutureTask实现 任务完成就可获取到，即结果按照完成先后顺序输出，与放入线程池的顺序无关
 * @date 2020/6/3 10:55
 */
public class CompletionServiceClient {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService, new LinkedBlockingQueue<>(100));
        for (int i = 5; i > 0; i--) {
            completionService.submit(new Task(i));
        }
        executorService.shutdown();
        for (int i = 0; i < 5; i++) {
            // 检索并移除表示下一个已完成任务的 Future，如果目前不存在这样的任务，则等待。
            Future<String> future = completionService.take();
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println("线程 " + i + " 执行完成：" + future.get());
        }
        System.out.println("总耗时：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static class Task implements Callable<String> {

        private volatile int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public String call() throws Exception {
            TimeUnit.MILLISECONDS.sleep(i * 500);
            return "任务：" + i;
        }
    }
}
