package com.zync.source;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试客户端连接超时
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 21:54
 */
@Slf4j
public class TestConnectionTimeout {

    public static void main(String[] args) {
        // new ServerBootstrap().option() 是给 ServerSocketChannel 配置参数
        // new ServerBootstrap().childOption() 是给 SocketChannel 配置参数

        // new Bootstrap().option() 方法配置参数 给 SocketChannel 配置参数

        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
            bootstrap.handler(new LoggingHandler());

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8700);
            channelFuture.sync().channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            worker.shutdownGracefully();
        }
    }
}
