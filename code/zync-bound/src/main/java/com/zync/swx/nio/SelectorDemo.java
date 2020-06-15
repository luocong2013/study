package com.zync.swx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.nio
 * @description TODO
 * @date 2017-12-8 16:12
 */
public class SelectorDemo {

    private static final int BUF_SIZE = 256;
    private static final int TIMEOUT = 3000;

    public static void main(String[] args) throws IOException {
        //打开服务端Sockte
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //打开Selector
        Selector selector = Selector.open();

        //服务端 Socket 监听8080端口, 并配置为非阻塞模式
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);

        //将 channel 注册到 selector 中.
        //通常我们都是先注册一个 OP_ACCEPT 事件, 然后在 OP_ACCEPT 到来时, 再将这个 Channel 的 OP_READ
        //注册到 Selector 中.
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 通过调用 select 方法, 阻塞地等待 channel I/O 可操作
            if (selector.select(TIMEOUT) == 0) {
                System.out.println(".");
                continue;
            }

            // 获取 I/O 操作就绪的 SelectionKey, 通过 SelectionKey 可以知道哪些 Channel 的哪类 I/O 操作已经就绪.
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                // 当获取一个 SelectionKey 后, 就要将它删除, 表示我们已经对这个 IO 事件进行了处理.
                keyIterator.remove();
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // 当 OP_ACCEPT 事件到来时, 我们就有从 ServerSocketChannel 中获取一个 SocketChannel,
                    // 代表客户端的连接
                    // 注意, 在 OP_ACCEPT 事件中, 从 key.channel() 返回的 Channel 是 ServerSocketChannel.
                    // 而在 OP_WRITE 和 OP_READ 中, 从 key.channel() 返回的是 SocketChannel.
                    SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                    socketChannel.configureBlocking(false);
                    //在 OP_ACCEPT 到来时, 再将这个 Channel 的 OP_READ 注册到 Selector 中.
                    // 注意, 这里我们如果没有设置 OP_READ 的话, 即 interest set 仍然是 OP_CONNECT 的话, 那么 select 方法会一直直接返回.
                    socketChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(BUF_SIZE));
                } else if (key.isConnectable()) {

                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    long bytesRead = socketChannel.read(buffer);
                    if (bytesRead == -1) {
                        socketChannel.close();
                    } else if (bytesRead > 0) {
                        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        System.out.println("Get data length: " + bytesRead);
                    }
                } else if (key.isValid() && key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    buffer.flip();
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.write(buffer);
                    if (!buffer.hasRemaining()) {
                        key.interestOps(SelectionKey.OP_READ);
                    }
                    buffer.compact();
                }
            }
        }
    }
}
