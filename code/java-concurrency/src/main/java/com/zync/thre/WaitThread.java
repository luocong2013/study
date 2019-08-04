package com.zync.thre;

import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 等待&通知
 * @date 2019/8/4 16:53
 */
public class WaitThread {

    private static final Object OBJECT = new Object();

    public static class T1 extends Thread {
        @Override
        public void run() {
            synchronized (OBJECT) {
                System.out.println(System.currentTimeMillis() + ": T1 start！");
                try {
                    System.out.println(System.currentTimeMillis() + ": T1 wait for object！");
                    OBJECT.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ": T1 end！");
            }
        }
    }

    public static class T2 extends Thread {
        @Override
        public void run() {
            synchronized (OBJECT) {
                System.out.println(System.currentTimeMillis() + ": T2 start！notify one thread.");
                OBJECT.notify();
                System.out.println(System.currentTimeMillis() + ": T2 end！");
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public static void main(String[] args){
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();
    }
}
