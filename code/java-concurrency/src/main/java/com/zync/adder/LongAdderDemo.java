package com.zync.adder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption LongAdder测试
 * @date 2019/12/1 15:19
 */
public class LongAdderDemo {
    /**
     * 线程数
     */
    private static final int MAX_THREADS = 3;
    /**
     * 任务数
     */
    private static final int TASK_COUNT = 3;
    /**
     * 目标总数
     */
    private static final int TARGET_COUNT = 10000000;

    private AtomicLong acount = new AtomicLong(0L);
    private LongAdder lacount = new LongAdder();
    private long count = 0;

    private static CountDownLatch cdlsync = new CountDownLatch(TASK_COUNT);
    private static CountDownLatch cdlatomic = new CountDownLatch(TASK_COUNT);
    private static CountDownLatch cdladdr = new CountDownLatch(TASK_COUNT);

    protected synchronized long inc() {
        return ++count;
    }

    protected synchronized long getCount() {
        return count;
    }

    public class SyncThread implements Runnable {
        protected long starttime;
        protected LongAdderDemo out;

        public SyncThread(long starttime, LongAdderDemo out) {
            this.starttime = starttime;
            this.out = out;
        }

        @Override
        public void run() {
            long v = out.getCount();
            while (v < TARGET_COUNT) {
                v = out.inc();
            }
            long endtime = System.currentTimeMillis();
            System.out.println("SyncThread spend: " + (endtime - starttime) + "ms" + " v = " + v);
            cdlsync.countDown();
        }
    }

    public void testSync() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREADS);
        long starttime = System.currentTimeMillis();
        SyncThread sync = new SyncThread(starttime, this);
        for (int i = 0; i < TASK_COUNT; i++) {
            service.submit(sync);
        }
        cdlsync.await();
        service.shutdown();
    }

    public class AtomicThread implements Runnable {

        protected long starttime;

        public AtomicThread(long starttime) {
            this.starttime = starttime;
        }

        @Override
        public void run() {
            long v = acount.get();
            while (v < TARGET_COUNT) {
                v = acount.incrementAndGet();
            }
            long endtime = System.currentTimeMillis();
            System.out.println("AtomicThread spend: " + (endtime - starttime) + "ms" + " v = " + v);
            cdlatomic.countDown();
        }
    }

    public void testAtomic() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREADS);
        long starttime = System.currentTimeMillis();
        AtomicThread atomic = new AtomicThread(starttime);
        for (int i = 0; i < TASK_COUNT; i++) {
            service.submit(atomic);
        }
        cdlatomic.await();
        service.shutdown();
    }

    public class LongAddrThread implements Runnable {
        protected long starttime;

        public LongAddrThread(long starttime) {
            this.starttime = starttime;
        }

        @Override
        public void run() {
            long v = lacount.sum();
            while (v < TARGET_COUNT) {
                lacount.increment();
                v = lacount.sum();
            }
            long endtime = System.currentTimeMillis();
            System.out.println("LongAddrThread spend: " + (endtime - starttime) + "ms" + " v = " + v);
            cdladdr.countDown();
        }
    }

    public void testLongAddr() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREADS);
        long starttime = System.currentTimeMillis();
        LongAddrThread longAddr = new LongAddrThread(starttime);
        for (int i = 0; i < TASK_COUNT; i++) {
            service.submit(longAddr);
        }
        cdladdr.await();
        service.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        LongAdderDemo longAddr = new LongAdderDemo();
        longAddr.testSync();
        longAddr.testAtomic();
        longAddr.testLongAddr();
    }
}
