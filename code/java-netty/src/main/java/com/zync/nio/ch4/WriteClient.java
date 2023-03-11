package com.zync.nio.ch4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 客户端接收服务端数据（使用selector）
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/10 22:37
 */
@Slf4j
public class WriteClient {

    public static void main(String[] args) throws IOException {
        try (Selector selector = Selector.open();
             SocketChannel sc = SocketChannel.open()) {
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
            sc.connect(new InetSocketAddress("localhost", 8000));

            // 接收数据
            int count = 0;
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isConnectable()) {
                        log.debug("finish connect...{}", sc.finishConnect());
                    } else if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
                        count += sc.read(buffer);
                        log.debug("count: {}", count);
                        buffer.clear();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
