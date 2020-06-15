package com.zync.swx.sum;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.sum
 * @description TODO
 * @date 2017-10-24 16:10
 */
public class SumTask extends RecursiveTask<Long> {
    private static final long TRUE_SHOLD = 50L;
    private long start;
    private long end;

    public SumTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean boo = (end - start) <= TRUE_SHOLD;
        if (boo) {
            sum = sum();
        } else {
            long mid = (start + end) >>> 1;
            SumTask t1 = new SumTask(start, mid);
            SumTask t2 = new SumTask(mid + 1, end);
            invokeAll(t1, t2);

            try {
                sum = t1.get() + t2.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            /* ②
            t1.fork();
            t2.fork();

            long sum1 = t1.join();
            long sum2 = t2.join();
            sum = sum1 + sum2;*/
        }
        return sum;
    }

    private long sum() {
        long sum = 0;
        for (long i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }
}
