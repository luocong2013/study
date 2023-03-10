package com.zync.nio.ch4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端接收服务端数据
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/10 22:37
 */
@Slf4j
public class WriteClient {

    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost", 8000));

        // 接收数据
        int count = 0;
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            count += channel.read(buffer);
            log.debug("count: {}", count);
            buffer.clear();
        }
    }
}
