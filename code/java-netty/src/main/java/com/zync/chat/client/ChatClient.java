package com.zync.chat.client;

import com.zync.chat.message.LoginRequestMessage;
import com.zync.chat.protocol.MessageCodecSharable;
import com.zync.chat.protocol.ProtocolFrameDecoder;
import com.zync.nio.ch5.MultiThreadFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.ThreadFactory;

/**
 * Chat 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 22:35
 */
@Slf4j
public class ChatClient {

    private static final ThreadFactory THREAD_FACTORY = new MultiThreadFactory("system-in-");

    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable messageCodec = new MessageCodecSharable();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    log.debug("connected...");
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ProtocolFrameDecoder());
                    pipeline.addLast(loggingHandler);
                    pipeline.addLast(messageCodec);
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        /**
                         * 会在连接建立后触发 active 事件
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 新启线程，负责接收用户在控制台的输入，负责向服务器发送各种消息
                            THREAD_FACTORY.newThread(() -> {
                                Scanner scanner = new Scanner(System.in);
                                System.out.println("请输入用户名：");
                                String username = scanner.nextLine();
                                System.out.println("请输入密码：");
                                String password = scanner.nextLine();
                                // 构造登录消息
                                LoginRequestMessage message = new LoginRequestMessage(username, password, null);
                                // 发送消息
                                ctx.writeAndFlush(message);
                            }).start();
                        }

                        /**
                         * 接收响应消息
                         * @param ctx
                         * @param msg
                         * @throws Exception
                         */
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.debug("msg: {}", msg);
                        }
                    });
                }
            });
            Channel channel = bootstrap.connect("127.0.0.1", 8888).sync().channel();
            log.debug("{} client start...", channel);
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            worker.shutdownGracefully();
            log.debug("client stop");
        }
    }
}
