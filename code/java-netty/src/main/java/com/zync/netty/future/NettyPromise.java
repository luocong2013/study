package com.zync.netty.future;

import com.zync.nio.ch5.MultiThreadFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * netty 的 promise 使用
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/15 12:48
 */
@Slf4j
public class NettyPromise {

    private static final ThreadFactory THREAD_FACTORY = new MultiThreadFactory("NettyPromise-");

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();

        // 创建 Promise 对象，用于存放结果
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);
        THREAD_FACTORY.newThread(() -> {
            log.debug("开始计算");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            promise.setSuccess(10);
        }).start();

        // 主线程中获得结果
        log.debug("等待结果...");
        log.debug("结果为: {}", promise.get());
    }
}
