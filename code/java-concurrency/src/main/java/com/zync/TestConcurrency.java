package com.zync;

import com.zync.pool.ThreadPoolHelper;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试并发量代码
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/16 17:02
 */
public class TestConcurrency {

    private static final Logger logger = LoggerFactory.getLogger(TestConcurrency.class);

    private static final ReentrantLock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        // 客户端数量
        int clientTotal = 500;
        // 并发数
        int threadTotal = 100;

        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            ThreadPoolHelper.executeTask(() -> {
                try {
                    semaphore.acquire();
                    doTask();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ThreadPoolHelper.shutdown();
    }

    private static void doTask() {
        try {
            if (!LOCK.tryLock(10, TimeUnit.MILLISECONDS)) {
                logger.warn("失败!");
                return;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.warn("成功!");
        try {
            TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(20, 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
            logger.warn("unlock!");
        }
    }

}
