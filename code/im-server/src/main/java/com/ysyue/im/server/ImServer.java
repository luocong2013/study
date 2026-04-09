package com.ysyue.im.server;

import com.ysyue.im.handler.ImMessageWebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Netty IM 服务器
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/23 9:49
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ImServer {

    // private final ImMessageHandler imMessageHandler;
    private final ImMessageWebSocketHandler imMessageWebSocketHandler;

    @Value("${im.server.port:9999}")
    private int port;

    @Value("${im.server.boss-threads:1}")
    private int bossThreads;

    @Value("${im.server.worker-threads:0}")
    private int workerThreads;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel serverChannel;

    public void start() throws InterruptedException {
        new Thread(this::run).start();
    }

    private void run() {
        // 处理连接请求
        bossGroup = new MultiThreadIoEventLoopGroup(bossThreads, NioIoHandler.newFactory());
        // 处理业务IO
        workerGroup = new MultiThreadIoEventLoopGroup(workerThreads > 0 ? workerThreads : Runtime.getRuntime().availableProcessors(), NioIoHandler.newFactory());

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) // 连接缓冲池
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true) // 禁用Nagle算法，降低延迟
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            // Web Socket 消息
                            // HTTP 编码解码器 (WebSocket是基于HTTP的)
                            pipeline.addLast(new HttpServerCodec());
                            // 支持大数据流
                            pipeline.addLast(new ChunkedWriteHandler());
                            // 将 HTTP 消息的多个部分合成一条完整的 HTTP 消息
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            // 心跳检测 (例如：60秒没收到客户端读请求则触发 userEventTriggered)
                            pipeline.addLast(new IdleStateHandler(60, 0, 0));
                            // 自动处理 WebSocket 握手及 Close/Ping/Pong 帧
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                            // 业务处理器
                            pipeline.addLast(imMessageWebSocketHandler);


                            // 1. 心跳检测: 300秒没收到客户端读请求，则触发 READER_IDLE
                            // pipeline.addLast(new IdleStateHandler(300, 0, 0, TimeUnit.SECONDS));
                            // 2. 处理粘包拆包 (最大包长度限制10MB，长度偏移量6，长度字节数4)
                            // pipeline.addLast(new LengthFieldBasedFrameDecoder(10485760, 6, 4, 0, 0));
                            // 3. 编解码器
                            // pipeline.addLast(new ImMessageDecoder());
                            // pipeline.addLast(new ImMessageEncoder());
                            // 4. 业务处理器
                            // pipeline.addLast(imMessageHandler);
                        }
                    });

            ChannelFuture future = bootstrap.bind(port).sync();
            serverChannel = future.channel();

            log.info("IM Server started on port {}", port);

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("IM Server interrupted", e);
        } finally {
            shutdown();
        }
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down IM Server");

        if (serverChannel != null) {
            serverChannel.close();
        }

        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }

        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }

        log.info("IM Server shutdown complete");
    }
}
