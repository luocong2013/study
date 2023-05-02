package com.zync.chat.client;

import com.zync.chat.client.handler.RpcResponseMessageHandler;
import com.zync.chat.message.RpcRequestMessage;
import com.zync.chat.protocol.MessageCodecSharable;
import com.zync.chat.protocol.ProtocolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * RPC客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/5/2 22:11
 */
@Slf4j
public class RpcClient {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable messageCodec = new MessageCodecSharable();
        RpcResponseMessageHandler rpcHandler = new RpcResponseMessageHandler();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ProtocolFrameDecoder());
                    pipeline.addLast(loggingHandler);
                    pipeline.addLast(messageCodec);
                    pipeline.addLast(rpcHandler);
                }
            });
            Channel channel = bootstrap.connect("localhost", 8555).sync().channel();
            log.debug("{} client start...", channel);

            ChannelFuture future = channel.writeAndFlush(new RpcRequestMessage(
                    1,
                    "com.zync.chat.service.HelloService",
                    "sayHello",
                    String.class,
                    new Class[]{String.class},
                    new Object[]{"张三111"}
            )).addListener(promise -> {
                if (!promise.isSuccess()) {
                    Throwable cause = promise.cause();
                    log.error("error", cause);
                }
            });

            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            worker.shutdownGracefully();
            log.debug("client stop");
        }
    }
}
