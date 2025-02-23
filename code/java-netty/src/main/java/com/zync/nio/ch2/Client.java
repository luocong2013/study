package com.zync.nio.ch2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * nio 阻塞模式 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/5 22:41
 */
public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost", 8000));
        System.out.println("waiting...");
    }
}
