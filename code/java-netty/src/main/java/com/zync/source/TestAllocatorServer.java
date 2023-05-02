package com.zync.source;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试 ALLOCATOR 和 RCVBUF_ALLOCATOR 服务端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/27 21:46
 */
@Slf4j
public class TestAllocatorServer {

    public static void main(String[] args) {
        ChannelFuture channelFuture = new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler());
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buffer = ctx.alloc().buffer();
                                log.debug("alloc buffer type is: {}", buffer);

                                log.debug("io buffer type is: {}", msg);
                            }
                        });
                    }
                }).bind(8777);
        try {
            channelFuture.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        }
    }
}
