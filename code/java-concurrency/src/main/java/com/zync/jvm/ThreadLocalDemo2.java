package com.zync.jvm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * jvm参数设置: -Xms25M -Xmx25M
 * @author admin
 * @version 1.0
 * @description 测试ThreadLocal内存溢出
 * @since 2025/2/7 11:33
 **/
public class ThreadLocalDemo2 {

    public static void main(String[] args) {
        ThreadLocal<User> userLocal = new ThreadLocal<>();

        ExecutorService executorService = Executors.newFixedThreadPool(30);

        for (int i = 0; i < 30; i++) {
            executorService.execute(() -> {
                userLocal.set(new User());
                // userLocal.remove();
            });
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();
    }
}
