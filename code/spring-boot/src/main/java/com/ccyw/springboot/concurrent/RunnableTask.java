package com.ccyw.springboot.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @description TODO
 * @date 2018/10/23 21:44
 */
public class RunnableTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RunnableTask.class);

    public RunnableTask() {
    }

    @Override
    public void run() {
        LOGGER.info(Thread.currentThread().getName() + "线程被调用了。");
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
