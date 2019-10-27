package com.zync.lock;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Guava 的 RateLimiter限流
 *             采用令牌桶算法
 * @date 2019/10/27 22:49
 */
public class RateLimiterDemo {

    /**
     * 每秒处理2个请求
     */
    private static RateLimiter limiter = RateLimiter.create(2);

    public static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis());
        }
    }

    public static void main(String[] args){
        for (int i = 0; i < 50; i++) {
            limiter.acquire();
            new Thread(new Task()).start();
        }

        System.out.println("使用 tryAcquire.");
        for (int i = 0; i < 50; i++) {
            if (!limiter.tryAcquire()) {
                continue;
            }
            new Thread(new Task()).start();
        }
    }
}
