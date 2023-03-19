package com.zync.advance.ch1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty 粘包半包 现象 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/19 19:44
 */
@Slf4j
public class HelloWorldClient {

    public static void main(String[] args) {
        HelloWorldClient client = new HelloWorldClient();
        for (int i = 0; i < 10; i++) {
            client.start();
        }
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
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 会在连接 channel 建立成功后 触发 active 事件
                            log.debug("sending...");
                            // 会产生 粘包 现象
                            //for (int i = 0; i < 1000; i++) {
                            //    ByteBuf buffer = ctx.alloc().buffer(16);
                            //    buffer.writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
                            //    ctx.writeAndFlush(buffer);
                            //}

                            // ① 粘包解决方案一 客户端改造 短链接
                            ByteBuf buffer = ctx.alloc().buffer(16);
                            buffer.writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17});
                            ctx.writeAndFlush(buffer);
                            ctx.channel().close();
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
}
