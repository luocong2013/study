package com.redis.redisdemo.task;

import com.redis.redisdemo.common.Const;
import com.redis.redisdemo.lock.RedisLock;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @description 任务1
 * @date 2020/5/26 13:43
 */
public class Task1 implements Runnable {

    private final RedisLock redisLock;

    public Task1(RedisLock redisLock) {
        this.redisLock = redisLock;
    }

    @Override
    public void run() {
        while (true) {
            String value = UUID.randomUUID().toString();
            if (redisLock.acquire(Const.REDIS_LOCK_KEY, value, 10, TimeUnit.SECONDS)) {
                try {
                    System.out.println("TASK1 init ... " + Thread.currentThread().getName() + " " + System.currentTimeMillis());
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("TASK1 end ... " + Thread.currentThread().getName() + " " + System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    redisLock.release(Const.REDIS_LOCK_KEY, value);
                }
            } else {
                System.out.println("TASK1 未获取到锁");
            }
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
