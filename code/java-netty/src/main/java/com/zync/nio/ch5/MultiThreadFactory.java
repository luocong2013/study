package com.zync.nio.ch5;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程版本ThreadFactory
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/11 20:11
 */
public class MultiThreadFactory implements ThreadFactory {

    private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
    private final AtomicInteger threadNumber = new AtomicInteger();
    private final String prefix;

    public MultiThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = defaultFactory.newThread(r);
        thread.setName(this.prefix + threadNumber.getAndIncrement());
        return thread;
    }
}
