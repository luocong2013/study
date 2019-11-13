package com.zync.jvm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 测试ThreadLocal GC
 * @date 2019/11/12 23:07
 */
public class ThreadLocalDemo {

    private static volatile ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + " is gc");
        }
    };

    private static volatile CountDownLatch cd = new CountDownLatch(10000);

    public static class ParseDate implements Runnable {

        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if (t1.get() == null) {
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                        @Override
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString() + " is gc");
                        }
                    });
                    System.out.println(Thread.currentThread().getId() + ": create SimpleDateFormat");
                }
                Date t = t1.get().parse("2019-11-12 23:12:" + i % 60);
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                cd.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            service.execute(new ParseDate(i));
        }
        cd.await();
        System.out.println("mission complete！！");
        t1 = null;
        System.gc();
        System.out.println("first GC complete！！");

        // 在设置ThreadLocal的时候，会清除ThreadLocalMap中的无效对象
        t1 = new ThreadLocal<>();
        cd = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            service.execute(new ParseDate(i));
        }
        cd.await();
        Thread.sleep(1000);
        System.gc();
        System.out.println("second GC complete！！");
    }
}
