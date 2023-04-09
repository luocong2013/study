package com.zync.chat.server;

import com.zync.chat.protocol.MessageCodecSharable;
import com.zync.chat.protocol.ProtocolFrameDecoder;
import com.zync.chat.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Chat 服务端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 21:44
 */
@Slf4j
public class ChatServer {

    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable messageCodec = new MessageCodecSharable();
        LoginRequestMessageHandler loginHandler = new LoginRequestMessageHandler();
        ChatRequestMessageHandler chatHandler = new ChatRequestMessageHandler();
        GroupChatRequestMessageHandler groupChatHandler = new GroupChatRequestMessageHandler();
        GroupCreateRequestMessageHandler groupCreateHandler = new GroupCreateRequestMessageHandler();
        GroupMembersRequestMessageHandler groupMembersHandler = new GroupMembersRequestMessageHandler();
        GroupJoinRequestMessageHandler groupJoinHandler = new GroupJoinRequestMessageHandler();
        GroupQuitRequestMessageHandler groupQuitHandler = new GroupQuitRequestMessageHandler();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.group(boss, worker);
            bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ProtocolFrameDecoder());
                    pipeline.addLast(loggingHandler);
                    pipeline.addLast(messageCodec);
                    pipeline.addLast(loginHandler);
                    pipeline.addLast(chatHandler);
                    pipeline.addLast(groupChatHandler);
                    pipeline.addLast(groupCreateHandler);
                    pipeline.addLast(groupMembersHandler);
                    pipeline.addLast(groupJoinHandler);
                    pipeline.addLast(groupQuitHandler);
                }
            });
            Channel channel = bootstrap.bind(8888).sync().channel();
            log.debug("{} server start...", channel);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            log.debug("server stop");
        }
    }
}
