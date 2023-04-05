package com.zync.advance.ch2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 协议解析-Redis
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:01
 */
@Slf4j
public class TestRedis {

    public static void main(String[] args) {
        final byte[] LINE = {13, 10};
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    log.debug("connected...");
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ByteBuf buffer = ctx.alloc().buffer();
                            buffer.writeBytes("*3".getBytes(StandardCharsets.UTF_8));
                            buffer.writeBytes(LINE);
                            buffer.writeBytes("$3".getBytes(StandardCharsets.UTF_8));
                            buffer.writeBytes(LINE);
                            buffer.writeBytes("set".getBytes(StandardCharsets.UTF_8));
                            buffer.writeBytes(LINE);
                            buffer.writeBytes("$4".getBytes(StandardCharsets.UTF_8));
                            buffer.writeBytes(LINE);
                            buffer.writeBytes("name".getBytes(StandardCharsets.UTF_8));
                            buffer.writeBytes(LINE);
                            buffer.writeBytes("$8".getBytes(StandardCharsets.UTF_8));
                            buffer.writeBytes(LINE);
                            buffer.writeBytes("zhangsan".getBytes(StandardCharsets.UTF_8));
                            buffer.writeBytes(LINE);
                            ctx.writeAndFlush(buffer);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf buffer = (ByteBuf) msg;
                            String response = buffer.toString(StandardCharsets.UTF_8);
                            log.debug("redis response: {}", response);
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6379).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error", e);
        } finally {
            worker.shutdownGracefully();
        }
    }

}
