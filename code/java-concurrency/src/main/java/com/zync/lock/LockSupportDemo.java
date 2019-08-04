package com.zync.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 线程阻塞工具类：LockSupport
 * @date 2019/8/4 18:50
 */
public class LockSupportDemo {

    private static Object object = new Object();

    private static ChangeObjectThread t1 = new ChangeObjectThread("t1");

    private static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {

        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (object) {
                System.out.println("in " + getName());
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        TimeUnit.MILLISECONDS.sleep(2000);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }

}
