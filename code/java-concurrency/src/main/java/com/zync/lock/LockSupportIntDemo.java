package com.zync.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption LockSupport.park() 方法支持中断响应
 *             但是和其他接收中断的函数很不一样，它不会抛出 InterruptedException 异常
 *             它只会默默返回
 * @date 2019/10/27 22:27
 */
public class LockSupportIntDemo {
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
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(getName() + " 被中断了.");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        TimeUnit.MILLISECONDS.sleep(2000);
        t2.start();
        t1.interrupt();
        LockSupport.unpark(t2);
    }
}
