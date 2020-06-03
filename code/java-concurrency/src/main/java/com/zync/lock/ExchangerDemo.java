package com.zync.lock;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @version V1.0.0
 * @description Exchanger 两个线程之间数据交换，Java并发包中的一个工具类。
 *              通过exchange方法相互交换数据，如果第一个执行到exchange方法，
 *              会等待第二个线程执行exchange，当两个线程都到达时，会进行数据交换。
 * @date 2020/6/3 14:01
 */
public class ExchangerDemo {

    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(new Producer(exchanger), "Producer").start();
        new Thread(new Consumer(exchanger), "Consumer").start();
        Thread.currentThread().join();
    }

    private static class Consumer implements Runnable {

        private Exchanger<String> exchanger;
        private String data;

        public Consumer(Exchanger<String> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    data = "data";
                    System.out.println(Thread.currentThread().getName() + " 交换前数据：" + data);
                    TimeUnit.SECONDS.sleep(1);
                    data = exchanger.exchange(data);
                    System.out.println(Thread.currentThread().getName() + " 交换后数据：" + data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Producer implements Runnable {

        private Exchanger<String> exchanger;
        private String data;

        public Producer(Exchanger<String> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i = 1; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    data = "数据_" + i;
                    System.out.println(Thread.currentThread().getName() + " 交换前数据：" + data);
                    data = exchanger.exchange(data);
                    System.out.println(Thread.currentThread().getName() + " 交换后数据：" + data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
