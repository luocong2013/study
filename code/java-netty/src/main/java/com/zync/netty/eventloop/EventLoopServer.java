package com.zync.netty.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * EventLoop 服务端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/12 19:31
 */
@Slf4j
public class EventLoopServer {

    public static void main(String[] args) {
        // 细分2：创建独立的 EventLoopGroup
        EventLoopGroup group = new DefaultEventLoopGroup();
        new ServerBootstrap()
                // boss 和 worker
                // 细分1：boss 只负责 ServerSocketChannel 上的accept事件；worker 只负责 SocketChannel 上的读写事件
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.debug("{}", buf.toString(StandardCharsets.UTF_8));
                                // 让消息传递给下一个handler
                                //ctx.fireChannelRead(msg);
                                super.channelRead(ctx, msg);
                            }
                        });
                        // 如果此操作很耗时，可以使用独立的 EventLoopGroup 来处理，避免 worker 的 EventLoopGroup 被长时间占用
                        ch.pipeline().addLast(group, "handler2", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.debug("{}", buf.toString(StandardCharsets.UTF_8));
                            }
                        });
                    }
                })
                .bind(8610);
    }
}
