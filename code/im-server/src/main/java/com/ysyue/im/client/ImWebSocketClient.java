package com.ysyue.im.client;

import com.ysyue.im.protocol.ImMessage;
import com.ysyue.im.protocol.MessageType;
import com.ysyue.im.util.JacksonUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Scanner;
import java.util.UUID;

/**
 * IM WebSocket 客户端测试工具
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/10 14:31
 **/
@Slf4j
public class ImWebSocketClient {

    private final String host;
    private final int port;
    private final String userId;

    private Channel channel;
    private EventLoopGroup group;
    private WebSocketClientHandler handler;

    public ImWebSocketClient(String host, int port, String userId) {
        this.host = host;
        this.port = port;
        this.userId = userId;
    }

    public void connect() throws Exception {
        URI uri = new URI("ws://" + host + ":" + port + "/ws");

        group = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

        handler = new WebSocketClientHandler(
                WebSocketClientHandshakerFactory.newHandshaker(
                        uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()));

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpClientCodec());
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new HttpObjectAggregator(65536));
                        pipeline.addLast(handler);
                    }
                });

        channel = bootstrap.connect(host, port).sync().channel();

        handler.getHandshaker().handshake(channel).sync();
        log.info("WebSocket 连接成功");

        handler.handshakeFuture().sync();

        autoLogin();
    }

    private void autoLogin() {
        ImMessage loginMsg = ImMessage.builder()
                .type(MessageType.LOGIN)
                .senderId(userId)
                .messageId(UUID.randomUUID().toString())
                .timestamp(System.currentTimeMillis())
                .build();

        sendMessage(loginMsg);
        log.info("自动登录发送：{}", userId);
    }

    public void sendMessage(String receiverId, String content) {
        ImMessage message = ImMessage.builder()
                .type(MessageType.CHAT)
                .senderId(userId)
                .receiverId(receiverId)
                .content(content)
                .messageId(UUID.randomUUID().toString())
                .timestamp(System.currentTimeMillis())
                .build();

        sendMessage(message);
        log.info("消息已发送：{}", content);
    }

    public void sendHeartbeat() {
        ImMessage heartbeat = ImMessage.builder()
                .type(MessageType.HEARTBEAT)
                .senderId(userId)
                .timestamp(System.currentTimeMillis())
                .build();

        sendMessage(heartbeat);
        log.debug("心跳已发送");
    }

    private void sendMessage(ImMessage message) {
        if (channel == null || !channel.isActive()) {
            log.error("未连接到服务器");
            return;
        }

        String json = JacksonUtil.toJson(message);
        TextWebSocketFrame frame = new TextWebSocketFrame(json);
        channel.writeAndFlush(frame);
    }

    public void disconnect() {
        if (channel != null) {
            ImMessage logout = ImMessage.builder()
                    .type(MessageType.LOGOUT)
                    .senderId(userId)
                    .timestamp(System.currentTimeMillis())
                    .build();

            sendMessage(logout);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            channel.close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
    }

    @ChannelHandler.Sharable
    static class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

        private final WebSocketClientHandshaker handshaker;
        private ChannelPromise handshakeFuture;

        public WebSocketClientHandler(WebSocketClientHandshaker handshaker) {
            this.handshaker = handshaker;
        }

        public ChannelPromise handshakeFuture() {
            return handshakeFuture;
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            handshakeFuture = ctx.newPromise();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            log.info("连接到服务器：{}", ctx.channel().remoteAddress());

        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            Channel ch = ctx.channel();

            if (!handshaker.isHandshakeComplete()) {
                handshaker.finishHandshake(ch, (FullHttpResponse) msg);
                handshakeFuture.setSuccess();
                log.info("WebSocket 握手完成");
                return;
            }

            if (msg instanceof FullHttpResponse response) {
                throw new IllegalStateException("Unexpected FullHttpResponse: " + response);
            }

            if (msg instanceof TextWebSocketFrame textFrame) {
                String text = textFrame.text();
                log.info("收到消息：{}", text);
                System.out.println("\n<<< 收到消息: " + text);
                System.out.print("> ");
            } else if (msg instanceof PingWebSocketFrame pingFrame) {
                ch.writeAndFlush(new PongWebSocketFrame(pingFrame.content()));
            } else if (msg instanceof CloseWebSocketFrame closeFrame) {
                log.info("收到关闭帧");
                ch.close();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.error("发生异常", cause);
            if (!handshakeFuture.isDone()) {
                handshakeFuture.setFailure(cause);
            }
            ctx.close();
        }

        public WebSocketClientHandshaker getHandshaker() {
            return handshaker;
        }
    }

    public static void main(String[] args) throws Exception {
        String userId = "user_" + System.currentTimeMillis();
        ImWebSocketClient client = new ImWebSocketClient("127.0.0.1", 9999, userId);

        try {
            client.connect();

            Scanner scanner = new Scanner(System.in);
            System.out.println("=== IM WebSocket 客户端 ===");
            System.out.println("当前用户: " + userId);
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
                    System.out.println("未知命令，支持：chat, heartbeat, quit");
                }
            }

            client.disconnect();
            scanner.close();
        } catch (Exception e) {
            log.error("客户端运行异常", e);
            client.disconnect();
        }
    }
}
