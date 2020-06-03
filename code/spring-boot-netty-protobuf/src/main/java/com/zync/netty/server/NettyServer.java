package com.zync.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.zync.netty.protobuf.UserData.UserMessage.getDefaultInstance;

/**
 * @author luocong
 * @version V1.0.0
 * @description 服务端
 * @date 2020/6/3 17:02
 */
@Slf4j
@Component
public class NettyServer implements InitializingBean, DisposableBean {

    @Value("${server.bind_port}")
    private Integer port;

    @Value("${server.netty.boss_group_thread_count}")
    private Integer bossGroupThreadCount;

    @Value("${server.netty.worker_group_thread_count}")
    private Integer workerGroupThreadCount;

    @Value("${server.netty.leak_detector_level}")
    private String leakDetectorLevel;

    @Autowired
    private NettyServerHandler nettyServerHandler;

    private ChannelFuture channelFuture;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Setting resource leak detector level to {}",leakDetectorLevel);
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(leakDetectorLevel.toUpperCase()));

        log.info("Starting Server");
        // 创建boss线程组 用于服务端接受客户端的连接
        bossGroup = new NioEventLoopGroup(bossGroupThreadCount);
        // 创建 worker 线程组 用于进行 SocketChannel 的数据读写
        workerGroup = new NioEventLoopGroup(workerGroupThreadCount);
        // 创建 ServerBootstrap 对象
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline ph = ch.pipeline();

                        // 入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
                        ph.addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));
                        ph.addLast(new ProtobufVarint32FrameDecoder());
                        ph.addLast(new ProtobufDecoder(getDefaultInstance()));
                        ph.addLast(new ProtobufVarint32LengthFieldPrepender());
                        ph.addLast(new ProtobufEncoder());
                        ph.addLast("nettyServerHandler", nettyServerHandler);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        // 绑定端口，并同步等待成功，即启动服务端
        channelFuture = b.bind(port).sync();
        log.info("Server started listen at {}", port);
    }

    @Override
    public void destroy() throws Exception {
        log.info("Stopping Server");
        try {
            // 监听服务端关闭，并阻塞等待
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅关闭两个 EventLoopGroup 对象
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        log.info("server stopped!");
    }
}
