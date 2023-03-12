package com.zync.netty.eventloop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 测试 EventLoop
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/12 19:00
 */
@Slf4j
public class TestEventLoop {

    public static void main(String[] args) {
        log.debug("{}", NettyRuntime.availableProcessors());

        // 1. 创建事件循环组
        // NioEventLoopGroup 可以处理 io事件、普通任务、定时任务
        // DefaultEventLoopGroup 可以处理 普通任务、定时任务
        EventLoopGroup group = new NioEventLoopGroup(2);
        // 2. 获取下一个事件循环对象
        log.debug("{}", group.next());
        log.debug("{}", group.next());
        log.debug("{}", group.next());
        log.debug("{}", group.next());

        // 3. 执行普通任务
        group.next().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("ok");
        });

        // 4. 执行定时任务
        group.next().scheduleAtFixedRate(() -> {
            log.debug("scheduleAtFixedRate");
        }, 1, 2, TimeUnit.SECONDS);

        log.debug("main");
    }
}
