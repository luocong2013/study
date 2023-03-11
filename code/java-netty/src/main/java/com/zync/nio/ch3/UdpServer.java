package com.zync.nio.ch3;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static com.zync.nio.utils.ByteBufferUtil.debugAll;

/**
 * UDP 服务端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/11 20:30
 */
@Slf4j
public class UdpServer {

    public static void main(String[] args) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.socket().bind(new InetSocketAddress(9000));
            log.debug("waiting...");
            ByteBuffer buffer = ByteBuffer.allocate(16);
            channel.receive(buffer);
            buffer.flip();
            debugAll(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
