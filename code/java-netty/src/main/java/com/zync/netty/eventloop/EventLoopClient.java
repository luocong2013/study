package com.zync.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * EventLoop 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/12 19:33
 */
@Slf4j
public class EventLoopClient {

    public static void main(String[] args) throws InterruptedException {
        // 1. 带有 Future，Promise 的类型都是和异步方法配套使用，用来正确处理结果
        ChannelFuture channelFuture = new Bootstrap()
                // 2. 添加 EventLoop
                .group(new NioEventLoopGroup())
                // 3. 选择客户端 channel 实现
                .channel(NioSocketChannel.class)
                // 4. 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    // 在建立连接后被调用
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));
                    }
                })
                // 5. 连接到服务器
                // 异步非阻塞，主线程发起调用，真正执行 connect 是 NioEventLoopGroup 中的 nio 线程
                .connect(new InetSocketAddress("localhost", 8610));

        // 1.1 使用 sync 方法同步处理结果
        // 阻塞住当前线程，直到nio线程连接建立完毕
        channelFuture.sync();
        Channel channel = channelFuture.channel();
        log.debug("{}", channel);
        channel.writeAndFlush("hi");

        // 1.2 使用 addListener(回调对象) 方法异步处理结果
        //channelFuture.addListener(new ChannelFutureListener() {
        //    // 在 nio 线程连接建立好之后，会调用 operationComplete
        //    @Override
        //    public void operationComplete(ChannelFuture future) throws Exception {
        //        Channel channel = future.channel();
        //        log.debug("{}", channel);
        //        channel.writeAndFlush("hi");
        //    }
        //});
    }
}
