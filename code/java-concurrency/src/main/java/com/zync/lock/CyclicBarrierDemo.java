package com.zync.lock;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 循环栅栏：CyclicBarrier
 * @date 2019/8/4 18:19
 */
public class CyclicBarrierDemo {

    public static class Soldier implements Runnable {

        private String soldier;

        private final CyclicBarrier cyclic;

        public Soldier(String soldier, CyclicBarrier cyclic) {
            this.soldier = soldier;
            this.cyclic = cyclic;
        }

        @Override
        public void run() {
            try {
                // 等待所有士兵到齐
                cyclic.await();
                doWork();
                // 等待所有士兵完成工作
                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void doWork() {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier + ": 任务完成！");
        }
    }

    public static class BarrierRun implements Runnable {

        private boolean flag;

        private int count;

        public BarrierRun(boolean flag, int count) {
            this.flag = flag;
            this.count = count;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("司令: [士兵" + count + "个，任务完成！]");
            } else {
                System.out.println("司令: [士兵" + count + "个，集合完毕！]");
                flag = true;
            }
        }
    }

    public static void main(String[] args){
        final int count = 15;
        final boolean flag = false;
        Thread[] allSoldier = new Thread[count];

        CyclicBarrier cyclic = new CyclicBarrier(count, new BarrierRun(flag, count));

        // 设置屏障点，主要是为了执行这个方法
        System.out.println("集合队伍！");
        for (int i = 0; i < count; i++) {
            System.out.println("士兵_" + i + "报道！");
            allSoldier[i] = new Thread(new Soldier("士兵_" + i, cyclic));
            allSoldier[i].start();
        }
    }
}
