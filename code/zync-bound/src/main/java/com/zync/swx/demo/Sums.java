package com.zync.swx.demo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author LC
 * @version V1.0
 * @project luoc-hanlp
 * @package com.luo.demo
 * @description TODO
 * @date 2017-10-20 16:26
 */
public class Sums {
    static class Sum implements Callable<Long> {
        private final long from;
        private final long to;
        Sum(long from, long to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public Long call() throws Exception {
            long acc = 0;
            for (long i = from; i <= to; i++) {
                acc += i;
            }
            return acc;
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<Long>> results = executorService.invokeAll(Arrays.asList(
                new Sum(0, 10), new Sum(100, 1_100), new Sum(10_100, 1_000_000), new Sum(0, 1000000000)
        ));
        executorService.shutdown();
        for (Future<Long> result : results) {
            System.out.println(result.get());
        }
    }
}
