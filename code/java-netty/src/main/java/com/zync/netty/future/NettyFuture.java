package com.zync.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * netty 的 future 使用
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/14 13:01
 */
@Slf4j
public class NettyFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        // 获得 EventLoop 对象
        EventLoop eventLoop = group.next();
        Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("开始计算");
                TimeUnit.SECONDS.sleep(1);
                return 60;
            }
        });

        // 主线程中获得结果
        log.debug("等待结果...");

        //log.debug("getNow: {}", future.getNow());
        //log.debug("结果为: {}", future.get());

        // NIO 线程中异步获取结果
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.debug("结果为: {}", future.getNow());
            }
        });
    }
}
