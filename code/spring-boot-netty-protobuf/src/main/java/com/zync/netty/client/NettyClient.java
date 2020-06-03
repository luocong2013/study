package com.zync.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.zync.netty.protobuf.UserData.UserMessage.getDefaultInstance;

/**
 * @author luocong
 * @version V1.0.0
 * @description 客户端
 * @date 2020/6/3 17:30
 */
@Slf4j
@Component
public class NettyClient implements InitializingBean, DisposableBean {

    @Value("${server.bind_address}")
    private String host;

    @Value("${server.bind_port}")
    private Integer port;

    @Autowired
    private NettyClientHandler nettyClientHandler;

    /**
     * 初始化标记
     */
    private AtomicBoolean init = new AtomicBoolean(true);
    private EventLoopGroup group;
    private ChannelFuture f;

    @Override
    public void afterPropertiesSet() throws Exception {
        group = new NioEventLoopGroup();
        connect(new Bootstrap(), group);
    }

    @Override
    public void destroy() throws Exception {
        log.info("正在停止客户端");
        try {
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
        log.info("客户端已停止!");
    }

    public void connect(Bootstrap b, EventLoopGroup group) {
        try {
            if (b == null) {
                return;
            }
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline ph = ch.pipeline();

                            // 入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
                            ph.addLast(new IdleStateHandler(0, 9, 0, TimeUnit.SECONDS));
                            ph.addLast(new ProtobufVarint32FrameDecoder());
                            ph.addLast(new ProtobufDecoder(getDefaultInstance()));
                            ph.addLast(new ProtobufVarint32LengthFieldPrepender());
                            ph.addLast(new ProtobufEncoder());
                            ph.addLast("nettyClientHandler", nettyClientHandler);
                        }
                    });
            f = b.connect(host, port).addListener((ChannelFuture future) -> {
                final EventLoop eventLoop = future.channel().eventLoop();
                if (!future.isSuccess()) {
                    log.info("与服务端端口连接！在10秒之后准备尝试重连！");
                    eventLoop.schedule(() -> connect(new Bootstrap(), eventLoop), 10, TimeUnit.SECONDS);
                }
            });
            if (init.getAndSet(false)) {
                log.info("Netty客户端连接成功！");
            }
        } catch (Exception e) {
            log.error("Netty客户端连接失败！", e);
        }
    }
}
