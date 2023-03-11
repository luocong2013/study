package com.zync.nio.ch3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

/**
 * UDP 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/11 20:32
 */
public class UdpClient {

    public static void main(String[] args) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            ByteBuffer buffer = StandardCharsets.UTF_8.encode("hello");
            InetSocketAddress address = new InetSocketAddress("localhost", 9000);
            channel.send(buffer, address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
