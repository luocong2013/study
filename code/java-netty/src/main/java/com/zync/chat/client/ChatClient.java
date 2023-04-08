package com.zync.chat.client;

import com.google.common.collect.Sets;
import com.zync.chat.message.*;
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
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

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
        CountDownLatch waitForLogin = new CountDownLatch(1);
        AtomicBoolean login = new AtomicBoolean(false);
        Scanner scanner = new Scanner(System.in);

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
                    //pipeline.addLast(loggingHandler);
                    pipeline.addLast(messageCodec);
                    pipeline.addLast("client handler", new ChannelInboundHandlerAdapter() {
                        /**
                         * 会在连接建立后触发 active 事件
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 新启线程，负责接收用户在控制台的输入，负责向服务器发送各种消息
                            THREAD_FACTORY.newThread(() -> {
                                System.out.println("请输入用户名：");
                                String username = scanner.nextLine();
                                System.out.println("请输入密码：");
                                String password = scanner.nextLine();
                                // 构造登录消息
                                LoginRequestMessage message = new LoginRequestMessage(username, password, null);
                                // 发送消息
                                ctx.writeAndFlush(message);

                                System.out.println("等待后续操作...");
                                try {
                                    waitForLogin.await();
                                } catch (InterruptedException e) {
                                    log.error("等待出错", e);
                                }
                                // 如果登录失败
                                if (!login.get()) {
                                    // 正常关闭客户端
                                    ctx.channel().close();
                                    return;
                                }

                                // 登录成功，进入菜单页
                                while (true) {
                                    System.out.println("==================================");
                                    System.out.println("send [username] [content]");
                                    System.out.println("gsend [group name] [content]");
                                    System.out.println("gcreate [group name] [m1,m2,m3...]");
                                    System.out.println("gmembers [group name]");
                                    System.out.println("gjoin [group name]");
                                    System.out.println("gquit [group name]");
                                    System.out.println("quit");
                                    System.out.println("==================================");
                                    String[] command;
                                    try {
                                        String line = scanner.nextLine();
                                        command = StringUtils.split(line, StringUtils.SPACE);
                                    } catch (Exception e) {
                                        break;
                                    }
                                    switch (command[0]) {
                                        case "send":
                                            ctx.writeAndFlush(new ChatRequestMessage(username, command[1], command[2]));
                                            break;
                                        case "gsend":
                                            ctx.writeAndFlush(new GroupChatRequestMessage(username, command[1], command[2]));
                                            break;
                                        case "gcreate":
                                            Set<String> set = Sets.newHashSet(StringUtils.split(command[2], ","));
                                            ctx.writeAndFlush(new GroupCreateRequestMessage(command[1], set));
                                            break;
                                        case "gmembers":
                                            ctx.writeAndFlush(new GroupMembersRequestMessage(command[1]));
                                            break;
                                        case "gjoin":
                                            ctx.writeAndFlush(new GroupJoinRequestMessage(username, command[1]));
                                            break;
                                        case "gquit":
                                            ctx.writeAndFlush(new GroupQuitRequestMessage(username, command[1]));
                                            break;
                                        case "quit":
                                            ctx.channel().close();
                                            return;
                                        default:
                                            System.out.println("您输入的指令错误, 请重新输入...");
                                    }
                                }
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
                            if (msg instanceof LoginResponseMessage message) {
                                login.set(message.isSuccess());
                                // 唤醒 system-in- 线程
                                waitForLogin.countDown();
                            }
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
