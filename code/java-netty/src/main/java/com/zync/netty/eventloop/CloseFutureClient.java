package com.zync.netty.eventloop;

import com.zync.nio.ch5.MultiThreadFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ThreadFactory;

/**
 * ChannelFuture 关闭问题
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/12 21:46
 */
@Slf4j
public class CloseFutureClient {

    private static final ThreadFactory THREAD_FACTORY = new MultiThreadFactory("input-");

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    // 在建立连接后被调用
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // LoggingHandler 打印详细的 netty 调试信息
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));
                    }
                })
                .connect(new InetSocketAddress("localhost", 8610));

        channelFuture.sync();
        Channel channel = channelFuture.channel();
        log.debug("{}", channel);

        THREAD_FACTORY.newThread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    // close 异步操作 1s 之后
                    channel.close();
                    break;
                }
                channel.writeAndFlush(line);
            }
        }).start();

        // 获取 ClosedFuture 对象，1）同步处理关闭，2）异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();
        // 1）同步处理关闭
        log.debug("waiting close...");
        closeFuture.sync();
        log.debug("处理关闭之后的操作");
        // 优雅关闭
        group.shutdownGracefully();

        // 2）异步处理关闭
        //closeFuture.addListener(new ChannelFutureListener() {
        //    @Override
        //    public void operationComplete(ChannelFuture future) throws Exception {
        //        log.debug("处理关闭之后的操作");
        //        group.shutdownGracefully();
        //    }
        //});
    }
}
