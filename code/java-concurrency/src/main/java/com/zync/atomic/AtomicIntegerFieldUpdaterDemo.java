package com.zync.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption AtomicIntegerFieldUpdater测试，让普通变量也享受原子操作
 * @date 2019/11/13 23:48
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static class Candidate {
        int id;
        volatile int score;
    }

    private static final AtomicIntegerFieldUpdater<Candidate> scoreUpdater = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    /**
     * 检查Updater是否正确工作
     */
    private static AtomicInteger allScore = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final Candidate stu = new Candidate();
        Thread[] ts = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            ts[i] = new Thread(() -> {
                if (Math.random() > 0.4) {
                    scoreUpdater.incrementAndGet(stu);
                    allScore.incrementAndGet();
                }
            });
            ts[i].start();
        }
        for (int i = 0; i < 10000; i++) {
            ts[i].join();
        }
        System.out.println("score = " + stu.score);
        System.out.println("allScore = " + allScore);
    }
}
