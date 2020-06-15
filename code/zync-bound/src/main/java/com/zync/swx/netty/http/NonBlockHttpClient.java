package com.zync.swx.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.net.URI;
import java.util.Map;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.executor
 * @description TODO
 * @date 2017-12-12 16:53
 */
public class NonBlockHttpClient {
    public static EventLoopGroup workerGroup = new NioEventLoopGroup(2);
    public static Bootstrap b = new Bootstrap();
    public static final EventExecutorGroup executor = new DefaultEventExecutorGroup(1);
    static {
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);
    }

    public static Object lock = new Object();

    /**
     * 异步GET请求
     * @param url
     * @param head
     * @param httpHandler
     * @return
     */
    public static Boolean get(String url, Map<String, String> head, final HttpHandler httpHandler) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            Integer port = uri.getPort() < 0 ? 80 : uri.getPort();
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
            if (head == null) {
                request.headers().add("Host", domain);
                request.headers().add("User-Agent", "Mozilla/5.0(X11;Ubuntu;Linux x86_64;rv:44.0)Gecko/20100101 Firefox/44.0");
                request.headers().add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                request.headers().add("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
                request.headers().add("Connection", "Keep-alive");
                request.headers().add("Cache-Control", "max-age=0");
            } else {
                head.forEach((k, v) -> request.headers().add(k, v));
            }

            ChannelInitializer channelInitializer = new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    // client端接收到的是httpResponse，所以要使用HttpResponseDecoder进行解码
                    socketChannel.pipeline().addLast(new HttpResponseDecoder());
                    // client端发送的是httpRequest，所以要使用HttpRequestEncoder进行编码
                    socketChannel.pipeline().addLast(new HttpRequestEncoder());
                    socketChannel.pipeline().addLast(executor, new GeneralHandler(httpHandler));
                }
            };

            ChannelFuture f;
            synchronized (lock) {
                b.handler(channelInitializer);
                f = b.connect(domain, port).sync();
            }
            f.channel().writeAndFlush(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void close() {
        try {
            executor.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
