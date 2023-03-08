package com.zync.nio.ch4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

import static com.zync.nio.utils.ByteBufferUtil.debugAll;

/**
 * nio selector模式 服务端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/5 22:32
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        // 1. 创建selector，管理多个 Channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        // 2. 建立 selector 和 channel 的联系（注册）
        // SelectionKey 就是将来事件发生后，通过它可以知道是什么事件、那个channel的事件
        // 4 种事件类型：
        // accept - 会在有连接请求时触发
        // connect - 是客户端连接建立后触发
        // read - 可读事件
        // write - 可写事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // key 只关注 accept 事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register sscKey: {}", sscKey);

        ssc.bind(new InetSocketAddress(8000));

        while (true) {
            // 3. select 方法，没有事件发生，线程阻塞，有事件发生，线程才会恢复运行
            selector.select();
            // 4. 处理事件，selectionKeys 内部包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                log.debug("key: {}", key);
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    log.debug("sc: {}", sc);
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("register scKey: {}", scKey);
                } else if (key.isReadable()) {
                    try {
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        SocketChannel sc = (SocketChannel) key.channel();
                        int read = sc.read(buffer);
                        if (read == -1) {
                            key.cancel();
                        } else {
                            debugAll(buffer);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();
                    }
                }
            }

        }
    }
}
