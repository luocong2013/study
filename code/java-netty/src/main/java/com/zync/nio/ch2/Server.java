package com.zync.nio.ch2;

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
 * nio 阻塞模式 服务端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/5 22:32
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 1. 创建服务器
        ServerSocketChannel server = ServerSocketChannel.open();
        // 2. 绑定端口
        server.bind(new InetSocketAddress(8000));

        // 3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 4. accept 建立与客户端连接，SocketChannel 用来与客户端之间通信
            log.debug("connecting...");
            // 阻塞方法，线程停止运行
            SocketChannel socketChannel = server.accept();
            log.debug("connected... {}", socketChannel);
            channels.add(socketChannel);
            for (SocketChannel channel : channels) {
                // 5. 接收客户端发送的数据
                log.debug("before read... {}", channel);
                // 阻塞方法，线程停止运行
                channel.read(buffer);
                buffer.flip();
                debugRead(buffer);
                buffer.clear();
                log.debug("after read... {}", channel);
            }
        }
    }
}
