package com.zync.adder;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption LongAccumulator测试
 * @date 2019/12/1 16:11
 */
public class LongAccumulatorDemo {

    /**
     * 通过多线程访问若干个整数，并返回遇到的最大的那个数
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        LongAccumulator accumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);
        Thread[] ts = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            ts[i] = new Thread(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                long value = random.nextLong();
                accumulator.accumulate(value);
            });
            ts[i].start();
        }
        for (int i = 0; i < 1000; i++) {
            ts[i].join();
        }
        System.out.println(accumulator.longValue());
    }
}
