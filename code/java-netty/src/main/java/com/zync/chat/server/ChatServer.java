package com.zync.chat.server;

import com.zync.chat.protocol.MessageCodecSharable;
import com.zync.chat.protocol.ProtocolFrameDecoder;
import com.zync.chat.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
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
        GroupRemoveRequestMessageHandler groupRemoveHandler = new GroupRemoveRequestMessageHandler();
        GroupMembersRequestMessageHandler groupMembersHandler = new GroupMembersRequestMessageHandler();
        GroupJoinRequestMessageHandler groupJoinHandler = new GroupJoinRequestMessageHandler();
        GroupQuitRequestMessageHandler groupQuitHandler = new GroupQuitRequestMessageHandler();
        PingMessageHandler pingHandler = new PingMessageHandler();
        QuitHandler quit = new QuitHandler();

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

                    // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
                    // 5s 内如果没有收到 channel 的数据，会触发一个 IdleState#READER_IDLE 事件
                    pipeline.addLast(new IdleStateHandler(5, 0, 0));
                    pipeline.addLast(new ChannelDuplexHandler() {
                        /**
                         * 用来处理特殊事件
                         * @param ctx
                         * @param evt
                         * @throws Exception
                         */
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            if (evt instanceof IdleStateEvent event) {
                                // 触发了 读空闲事件
                                if (event.state() == IdleState.READER_IDLE) {
                                    log.debug("已经 5s 没有读到数据了");
                                    ctx.channel().close();
                                }
                            }
                        }
                    });

                    pipeline.addLast(loginHandler);
                    pipeline.addLast(chatHandler);
                    pipeline.addLast(groupChatHandler);
                    pipeline.addLast(groupCreateHandler);
                    pipeline.addLast(groupRemoveHandler);
                    pipeline.addLast(groupMembersHandler);
                    pipeline.addLast(groupJoinHandler);
                    pipeline.addLast(groupQuitHandler);
                    pipeline.addLast(pingHandler);
                    pipeline.addLast(quit);
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
