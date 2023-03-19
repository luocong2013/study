package com.zync.advance.ch1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Netty 黏包半包 定长数组 解决方案 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/19 22:37
 */
@Slf4j
public class FixedLengthClient {

    public static void main(String[] args) {
        new FixedLengthClient().start();
    }

    void start() {
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
                            ByteBuf buffer = ctx.alloc().buffer(16);
                            char c = '0';
                            Random r = new Random();
                            for (int i = 0; i < 10; i++) {
                                byte[] bytes = fill11Bytes(c, r.nextInt(11) + 1);
                                c++;
                                buffer.writeBytes(bytes);
                            }
                            ctx.writeAndFlush(buffer);
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8700).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("client error", e);
        } finally {
            worker.shutdownGracefully();
        }
    }

    byte[] fill11Bytes(char c, int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(c);
        }
        if (length < 11) {
            for (int i = length; i < 11; i++) {
                builder.append("_");
            }
        }
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
