package com.ysyue.im.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysyue.im.codec.ImMessageDecoder;
import com.ysyue.im.codec.ImMessageEncoder;
import com.ysyue.im.protocol.ImMessage;
import com.ysyue.im.protocol.MessageType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * IM 客户端测试工具
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/23 9:53
 **/
@Slf4j
public class ImClient {

    private final String host;
    private final int port;
    private final String userId;

    private Channel channel;
    private EventLoopGroup group;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ImClient(String host, int port, String userId) {
        this.host = host;
        this.port = port;
        this.userId = userId;
    }

    public void connect() throws InterruptedException {
        group = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 60, 0, TimeUnit.SECONDS));
                        pipeline.addLast(new ImMessageEncoder());
                        pipeline.addLast(new ImMessageDecoder());
                        pipeline.addLast(new SimpleChannelInboundHandler<ImMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ImMessage msg) throws Exception {
                                log.info("收到消息：{}", msg);
                                System.out.println("\n收到消息：" + msg.getContent());
                                System.out.print("> ");
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                log.info("连接到服务器：{}", ctx.channel().remoteAddress());
                                autoLogin(ctx);
                            }

                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                if (evt instanceof IdleStateEvent event) {
                                    if (event.state() == IdleState.WRITER_IDLE) {
                                        log.warn("Write idle timeout, closing connection: {}", ctx.channel().id());
                                        ctx.close();
                                    }
                                } else {
                                    super.userEventTriggered(ctx, evt);
                                }
                            }
                        });
                    }
                });

        ChannelFuture future = bootstrap.connect(host, port).sync();
        channel = future.channel();
        log.info("客户端启动成功");
    }

    private void autoLogin(ChannelHandlerContext ctx) {
        ImMessage loginMsg = ImMessage.builder()
                .type(MessageType.LOGIN)
                .senderId(userId)
                .messageId(UUID.randomUUID().toString())
                .timestamp(System.currentTimeMillis())
                .build();

        ctx.writeAndFlush(loginMsg);
        log.info("自动登录发送：{}", userId);
    }

    public void sendMessage(String receiverId, String content) {
        if (channel == null || !channel.isActive()) {
            log.error("未连接到服务器");
            return;
        }

        ImMessage message = ImMessage.builder()
                .type(MessageType.CHAT)
                .senderId(userId)
                .receiverId(receiverId)
                .content(content)
                .messageId(UUID.randomUUID().toString())
                .timestamp(System.currentTimeMillis())
                .build();

        channel.writeAndFlush(message);
        log.info("消息已发送：{}", content);
    }

    public void sendHeartbeat() {
        if (channel == null || !channel.isActive()) {
            return;
        }

        ImMessage heartbeat = ImMessage.builder()
                .type(MessageType.HEARTBEAT)
                .senderId(userId)
                .timestamp(System.currentTimeMillis())
                .build();

        channel.writeAndFlush(heartbeat);
        log.debug("心跳已发送");
    }

    public void disconnect() {
        if (channel != null) {
            ImMessage logout = ImMessage.builder()
                    .type(MessageType.LOGOUT)
                    .senderId(userId)
                    .timestamp(System.currentTimeMillis())
                    .build();

            channel.writeAndFlush(logout);
            channel.close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        ImClient client = new ImClient("127.0.0.1", 9999, "user_" + System.currentTimeMillis());
        client.connect();

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== IM 客户端 ===");
        System.out.println("输入命令：chat <userId> <message> | heartbeat | quit");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if ("quit".equals(input)) {
                break;
            }

            if (input.startsWith("chat ")) {
                String[] parts = input.split(" ", 3);
                if (parts.length >= 3) {
                    client.sendMessage(parts[1], parts[2]);
                } else {
                    System.out.println("格式错误：chat <userId> <message>");
                }
            } else if ("heartbeat".equals(input)) {
                client.sendHeartbeat();
            } else {
                System.out.println("未知命令");
            }
        }

        client.disconnect();
        scanner.close();
    }
}
