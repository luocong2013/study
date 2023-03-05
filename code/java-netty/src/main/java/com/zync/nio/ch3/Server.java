package com.zync.nio.ch3;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.zync.nio.utils.ByteBufferUtil.debugRead;

/**
 * nio 非阻塞模式 服务端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/5 22:32
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        // 单线程
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 1. 创建服务器
        ServerSocketChannel server = ServerSocketChannel.open();
        // 设置为非阻塞模式
        server.configureBlocking(false);
        // 2. 绑定端口
        server.bind(new InetSocketAddress(8000));

        // 3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 4. accept 建立与客户端连接，SocketChannel 用来与客户端之间通信
            // 非阻塞，线程还会继续运行，如果没有连接建立，那么socketChannel是null
            SocketChannel socketChannel = server.accept();
            if (socketChannel != null) {
                log.debug("connected... {}", socketChannel);
                // 设置为非阻塞模式
                socketChannel.configureBlocking(false);
                channels.add(socketChannel);
            }
            for (SocketChannel channel : channels) {
                // 5. 接收客户端发送的数据
                // 非阻塞，线程还会继续运行，如果没有读到数据 read 返回 0
                int read = channel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    debugRead(buffer);
                    buffer.clear();
                    log.debug("after read... {}", channel);
                }
            }
        }
    }
}
