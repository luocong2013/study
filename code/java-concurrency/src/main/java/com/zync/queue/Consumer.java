package com.zync.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 消费者
 * @date 2019/11/16 18:36
 */
public class Consumer implements Runnable {

    private BlockingQueue<PCData> queue;
    private static final int SLEEPTIME = 1000;

    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        PCData data = null;
        Random random = new Random();

        System.out.println("start Consumer id = " + Thread.currentThread().getId());
        try {
            while (true) {
                data = queue.take();
                if (data != null) {
                    int result = data.getIntData() * data.getIntData();
                    System.out.println(String.format("%d * %d = %d", data.getIntData(), data.getIntData(), result));
                    Thread.sleep(random.nextInt(SLEEPTIME));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
