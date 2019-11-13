package com.zync.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption AtomicIntegerArray测试，数组也能无锁
 * @date 2019/11/13 23:35
 */
public class AtomicIntegerArrayDemo {

    private static AtomicIntegerArray array = new AtomicIntegerArray(10);

    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int k = 0; k < 10000; k++) {
                array.getAndIncrement(k % array.length());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread [] ts = new Thread[10];
        for (int k = 0; k < 10; k++) {
            ts[k] = new Thread(new AddThread());
        }
        for (int k = 0; k < 10; k++) {
            ts[k].start();
        }
        for (int k = 0; k < 10; k++) {
            ts[k].join();
        }
        System.out.println(array);
    }
}
