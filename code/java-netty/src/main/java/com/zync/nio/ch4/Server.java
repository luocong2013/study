package com.zync.nio.ch4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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
            // select 在事件未处理时，它不会阻塞，事件发生后要么处理，要么取消
            selector.select();
            // 4. 处理事件，selectionKeys 内部包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 处理key时，要从selectedKeys集合中删除，否则下次处理就会有问题
                iterator.remove();
                log.debug("key: {}", key);
                // 5. 区分事件类型
                if (key.isAcceptable()) {
                    // 如果是 accept
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    log.debug("sc: {}", sc);
                    sc.configureBlocking(false);
                    // 将ByteBuffer作为附件关联到selectedKey上
                    ByteBuffer buffer = ByteBuffer.allocate(4);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("register scKey: {}", scKey);
                } else if (key.isReadable()) {
                    // 如果是 read
                    try {
                        SocketChannel sc = (SocketChannel) key.channel();
                        // 获取selectedKey上关联的附件
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        // 客户端断开了，read 方法会返回 -1
                        int read = sc.read(buffer);
                        if (read == -1) {
                            key.cancel();
                        } else {
                            //buffer.flip();
                            //debugAll(buffer);
                            //log.info(StandardCharsets.UTF_8.decode(buffer).toString());
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                                // 扩容
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                // 替换掉selectedKey上关联的附件
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 因为客户端断开了，因此需要将key取消（从selector的keys集合中真正删除key）
                        key.cancel();
                    }
                }
            }

        }
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                // 完整消息存入新的ByteBuffer
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从 source 读，向 target 写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }
}
