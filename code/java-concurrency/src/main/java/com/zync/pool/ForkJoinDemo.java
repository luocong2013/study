package com.zync.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption ForkJoin
 * @date 2019/8/5 22:18
 */
public class ForkJoinDemo {

    public static class CountTask extends RecursiveTask<Long> {

        private static final int THRESHOLD = 10000;

        private long start;

        private long end;

        public CountTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start < THRESHOLD) {
                long total = 0;
                for (long i = start; i <= end; i++) {
                    total += i;
                }
                return total;
            } else {
                long step = (start + end) / 2;
                CountTask task1 = new CountTask(start, step);
                CountTask task2 = new CountTask(step + 1, end);
                task1.fork();
                task2.fork();
                return task1.join() + task2.join();
            }
        }
    }

    public static void main(String[] args){
        ForkJoinPool pool = new ForkJoinPool();
        CountTask task = new CountTask(0, 1000L);
        ForkJoinTask<Long> result = pool.submit(task);
        try {
            Long total = result.get();
            System.out.println("total = " + total);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
