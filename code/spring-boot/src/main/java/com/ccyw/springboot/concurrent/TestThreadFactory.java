package com.ccyw.springboot.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author luoc
 * @version V1.0.0
 * @description TODO
 * @date 2018/10/23 21:44
 */
public class TestThreadFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestThreadFactory.class);

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 2;

    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 4;

    /**
     * 阻塞队列
     * ArrayBlockingQueue：构造函数一定要传大小
     * LinkedBlockingQueue：构造函数不传大小会默认为2147483647（Integer.MAX_VALUE），当大量请求任务时，容易造成内存耗尽OOM
     * SynchronousQueue：同步队列，一个没有存储空间的阻塞队列 ，将任务同步交付给工作线程
     * PriorityBlockingQueue：优先级队列
     *
     * LinkedBlockingQueue 与 LinkedBlockingDeque 的区别：
     * LinkedBlockingQueue是单向队列，FIFO
     * LinkedBlockingDeque是一个双向队列，可以在队列头和尾执行插入，既是一个队列，也是一个栈
     */
    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
    //private static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(10);
    //private static BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(10);
    //private static BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();
    //private static BlockingQueue<Runnable> workQueue = new PriorityBlockingQueue<>();

    /**
     * 线程工厂
     */
    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

    /**
     * 饱和策略
     * CallerRunsPolicy：这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功
     * AbortPolicy（默认）：对拒绝任务抛弃处理，并抛出异常
     * DiscardPolicy：对拒绝任务直接无声抛弃，没有异常信息
     * DiscardOldestPolicy：对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列
     */
    //private static RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
    private static RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
    //private static RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
    //private static RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 5000L, TimeUnit.MILLISECONDS,
            workQueue, threadFactory, handler);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            threadPoolExecutor.execute(new RunnableTask());
            LOGGER.info("**********************" + i + "*********************");
        }
        while (true) {
            LOGGER.info("【线程池任务】线程池中线程数：" + threadPoolExecutor.getPoolSize());
            LOGGER.info("【线程池任务】队列中等待执行的任务数：" + threadPoolExecutor.getQueue().size());
            LOGGER.info("【线程池任务】已执行完任务数：" + threadPoolExecutor.getCompletedTaskCount());
            LOGGER.info("【线程池任务】活动线程数: " + threadPoolExecutor.getActiveCount());
            LOGGER.info("【线程池任务】任务数: " + threadPoolExecutor.getTaskCount());
            //if (threadPoolExecutor.getQueue().size() == 0) {
            //    break;
            //}
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
