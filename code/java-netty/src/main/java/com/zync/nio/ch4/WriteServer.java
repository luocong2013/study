package com.zync.nio.ch4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * 服务端写大量数据给客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/10 22:30
 */
@Slf4j
public class WriteServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8000));

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, SelectionKey.OP_READ, null);

                    // 1. 向客户端发送大量数据
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < 3000000; i++) {
                        builder.append("a");
                    }
                    ByteBuffer buffer = StandardCharsets.UTF_8.encode(builder.toString());
                    // 2. 返回值代表实际写入的字节数
                    int write = sc.write(buffer);
                    log.debug("write: {}", write);

                    // 3. 判断是否有剩余内容
                    if (buffer.hasRemaining()) {
                        // 4. 关注可写事件
                       scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                       //scKey.interestOpsOr(SelectionKey.OP_WRITE);
                        // 5. 把未写完的数据关联到 SelectionKey 上
                        scKey.attach(buffer);
                    }
                } else if (key.isReadable()) {

                } else if (key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int write = sc.write(buffer);
                    log.debug("write: {}", write);
                    // 6. 清理buffer
                    if (!buffer.hasRemaining()) {
                        key.attach(null);
                        // 不需要再关注可写事件
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
            }
        }

    }
}
