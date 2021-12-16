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
 * redis 抢锁执行结果
 *
 * 500 客户端, 重复抢锁 3 次, 模拟业务执行时间 20-100 毫秒
 * 200 并发 成功 38
 * 100 并发 成功 60
 * 50 并发 成功 115
 * 20 并发 成功 222
 * 10 并发 成功 317
 * 5 并发 成功 412
 * 3 并发 成功 453
 * 2 并发 成功 474
 *
 * 500 客户端, 重复抢锁 5 次, 模拟业务执行时间 20-100 毫秒
 * 200 并发 成功 60
 * 100 并发 成功 100
 * 50 并发 成功 163
 * 20 并发 成功 317
 * 10 并发 成功 401
 * 5 并发 成功 461
 * 3 并发 成功 490
 * 2 并发 成功 492
 *
 *
 * 500 客户端, 重复抢锁 10 次, 模拟业务执行时间 20-100 毫秒
 * 200 并发 成功 121
 * 100 并发 成功 176
 * 50 并发 成功 303
 * 20 并发 成功 432
 * 10 并发 成功 485
 * 5 并发 成功 497
 * 3 并发 成功 499
 * 2 并发 成功 500
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
