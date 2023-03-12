package com.zync.netty.ch1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * netty 服务端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/12 14:35
 */
@Slf4j
public class HelloServer {

    public static void main(String[] args) {
        // 1. 服务端启动器
        new ServerBootstrap()
                // 2. BossEventLoop, WorkerEventLoop(selector, thread), group 组
                .group(new NioEventLoopGroup())
                // 3. 选择 服务器的 ServerSocketChannel 实现
                .channel(NioServerSocketChannel.class)
                // 4. boss 复负责处理连接；worker(child) 负责处理读写，决定了 worker(child) 能执行哪些操作（handler）
                .childHandler(
                        // 5. channel 代表和客户端进行数据读写的通道 Initializer 初始化，负责添加别的 handler
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 6. 添加具体的 handler
                        // StringDecoder 将 ByteBuf 转换为字符串
                        ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
                        // 自定义 handler
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                // 读事件，打印上一步转换好的字符串
                                log.info("{}", msg);
                            }
                        });
                    }
                })
                // 7. 绑定监听端口
                .bind(8600);
    }
}
