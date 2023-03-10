package com.zync.nio.ch4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * nio 非阻塞模式 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/5 22:41
 */
public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost", 8000));
        channel.write(StandardCharsets.UTF_8.encode("hello world\nhi\n"));
        System.out.println("waiting...");
    }
}
