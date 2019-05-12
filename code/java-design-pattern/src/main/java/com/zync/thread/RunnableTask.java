package com.zync.thread;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 执行任务
 * @date 2019/5/12 22:06
 */
public class RunnableTask implements Runnable {

    private RunnableDemo runnableDemo;

    public RunnableTask(RunnableDemo runnableDemo) {
        this.runnableDemo = runnableDemo;
    }

    @Override
    public void run() {
        runnableDemo.incrementCount();
    }
}
