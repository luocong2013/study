package com.zync.netty.ch1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * netty 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/12 14:53
 */
public class HelloClient {

    public static void main(String[] args) throws InterruptedException {
        // 1. 客户端启动器
        new Bootstrap()
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
                .connect(new InetSocketAddress("localhost", 8600))
                // 6. 阻塞方法，直到连接建立
                .sync()
                .channel()
                // 7. 向服务器发送数据
                .writeAndFlush("hello, world");
    }
}
