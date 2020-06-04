package com.zync.netty.client;

import io.netty.channel.Channel;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @version V1.0.0
 * @description 客户端定时任务线程池
 * @date 2020/6/4 9:33
 */
@Component
public class NettyClientPool {

    private static ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(4, new BasicThreadFactory.Builder().namingPattern("example-pool-%d").daemon(false).build());

    public void executeTask(Channel channel) {
        ScheduledFuture<?> scheduledFuture = pool.scheduleWithFixedDelay(new NettyClientTask(channel), 1, 8, TimeUnit.SECONDS);
        channel.attr(NettyClientConst.CLIENT_CONTEXT_KEY).set(scheduledFuture);
    }

    public void cancel(Channel channel) {
        ScheduledFuture<?> scheduledFuture = channel.attr(NettyClientConst.CLIENT_CONTEXT_KEY).get();
        scheduledFuture.cancel(true);
    }
}
