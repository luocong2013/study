package com.zync.list;

import java.util.ArrayList;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 并发下的ArrayList
 * @date 2019/10/20 20:01
 */
public class ArrayListMultiThread {

    private static ArrayList<Integer> list = new ArrayList<>(10);

    public static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000000; i++) {
                list.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new AddThread());
        Thread thread2 = new Thread(new AddThread());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(list.size());
    }
}
