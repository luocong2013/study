package com.zync.thre;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 线程优先级
 * @date 2019/10/26 21:17
 */
public class PriorityDemo {

    public static class HightPriority extends Thread {
        static int count = 0;

        @Override
        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if (count > 10000000) {
                        System.out.println("HightPriority is Complete.");
                        break;
                    }
                }
            }
        }
    }

    public static class LowPriority extends Thread {
        static int count = 0;

        @Override
        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if (count > 10000000) {
                        System.out.println("LowPriority is Complete.");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        Thread hight = new HightPriority();
        Thread low = new LowPriority();
        hight.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);
        low.start();
        hight.start();
    }
}
