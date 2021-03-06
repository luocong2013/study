package com.zync.thre;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 并发下的HashMap问题
 * @date 2019/10/27 9:59
 */
public class HashMapMultiThread {

    static Map<String, String> map = new HashMap<>(16);

    public static class AddThread implements Runnable {
        int start = 0;

        public AddThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < 100000; i += 2) {
                map.put(Integer.toString(i), Integer.toBinaryString(i));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread(0));
        Thread t2 = new Thread(new AddThread(1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(map.size());
    }
}
