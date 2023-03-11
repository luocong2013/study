package com.zync.nio.ch5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * nio selector 模式 客户端（多线程优化）
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/11 18:55
 */
public class MultiThreadClient {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8000));
        sc.write(StandardCharsets.UTF_8.encode("1234567890qwertyu"));
        System.in.read();
    }
}
