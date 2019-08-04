package com.zync.thre;

import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 线程中断
 * @date 2019/8/4 16:41
 */
public class IntThread {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    System.out.println(0);
                } catch (InterruptedException e) {
                    System.out.println("Interruted When Sleep.");
                    // 设置中断状态
                    // Thread.sleep()方法由于中断而抛出异常，此时，它会清除中断标记
                    // 所以这里再次设置中断标记位.
                    Thread.currentThread().interrupt();
                }
                Thread.yield();
            }
        });
        t1.start();
        TimeUnit.MILLISECONDS.sleep(2000);
        t1.interrupt();
    }
}
