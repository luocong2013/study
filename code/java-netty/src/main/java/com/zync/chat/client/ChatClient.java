package com.zync.chat.client;

import com.google.common.collect.Sets;
import com.zync.chat.message.*;
import com.zync.chat.protocol.MessageCodecSharable;
import com.zync.chat.protocol.ProtocolFrameDecoder;
import com.zync.chat.common.utils.Assert;
import com.zync.nio.ch5.MultiThreadFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
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
        AtomicBoolean exit = new AtomicBoolean(false);
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
                    pipeline.addLast(loggingHandler);
                    pipeline.addLast(messageCodec);

                    // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
                    // 4s 内如果没有向服务器写数据，会触发一个 IdleState#WRITER_IDLE 事件
                    pipeline.addLast(new IdleStateHandler(0, 4, 0));
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
                                // 触发了写空闲事件
                                if (event.state() == IdleState.WRITER_IDLE) {
                                    //log.debug("4s 没有写数据了，发送一个心跳包");
                                    ctx.writeAndFlush(new PingMessage());
                                }
                            }
                        }
                    });

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
                                if (exit.get()) {
                                    return;
                                }
                                System.out.println("请输入密码：");
                                String password = scanner.nextLine();
                                if (exit.get()) {
                                    return;
                                }
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

                                System.out.println("=================👏🏻欢迎您登录聊天系统👏🏻=================");
                                // 登录成功，进入菜单页
                                while (true) {
                                    System.out.println("=================操作菜单=================");
                                    System.out.println("send [username] [content]");
                                    System.out.println("gsend [group name] [content]");
                                    System.out.println("gcreate [group name] [m1,m2,m3...]");
                                    System.out.println("gremove [group name]");
                                    System.out.println("gmembers [group name]");
                                    System.out.println("gjoin [group name]");
                                    System.out.println("gquit [group name]");
                                    System.out.println("quit");
                                    System.out.println("==================操作菜单================");
                                    String[] command;
                                    try {
                                        String line = scanner.nextLine();
                                        command = StringUtils.split(line, StringUtils.SPACE);
                                        Assert.isTrue(ArrayUtils.getLength(command) > 0, () -> new IllegalArgumentException("您输入的指令有误"));
                                    } catch (Exception e) {
                                        System.out.println("您输入的指令有误, 请重新输入...");
                                        log.error("您输入的指令有误", e);
                                        continue;
                                    }
                                    if (exit.get()) {
                                        return;
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
                                            set.add(username);
                                            ctx.writeAndFlush(new GroupCreateRequestMessage(command[1], set));
                                            break;
                                        case "gremove":
                                            ctx.writeAndFlush(new GroupRemoveRequestMessage(command[1]));
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
                                            System.out.println("您输入的指令有误, 请重新输入...");
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

                        /**
                         * 当 服务端 断开 客户端 连接时触发
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                            log.debug("连接已经断开，按回车键退出...");
                            exit.set(true);
                        }

                        /**
                         * 在出现异常时触发
                         * @param ctx
                         * @param cause
                         * @throws Exception
                         */
                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            log.debug("连接已经断开，按回车键退出...{}", cause.getMessage());
                            exit.set(true);
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
