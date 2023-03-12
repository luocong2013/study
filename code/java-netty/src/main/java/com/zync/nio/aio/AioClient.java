package com.zync.nio.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * 网络 AIO 客户端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/12 14:05
 */
@Slf4j
public class AioClient {

    public static void main(String[] args) {
        try (AsynchronousSocketChannel channel = AsynchronousSocketChannel.open()) {
            channel.connect(new InetSocketAddress("localhost", 8000), null, new CompletionHandler<Void, Object>() {
                @Override
                public void completed(Void result, Object attachment) {
                    channel.write(StandardCharsets.UTF_8.encode("Hello!"), null, new CompletionHandler<Integer, Object>() {
                        @Override
                        public void completed(Integer result, Object attachment) {
                            try {
                                final ByteBuffer buffer = ByteBuffer.allocate(16);
                                channel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                                    @Override
                                    public void completed(Integer result, ByteBuffer attachment) {
                                        buffer.flip();
                                        log.info(StandardCharsets.UTF_8.decode(buffer).toString());
                                        try {
                                            channel.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void failed(Throwable exc, ByteBuffer attachment) {
                                        log.error("read failed...", exc);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {
                            log.error("write failed...", exc);
                        }
                    });
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    log.error("connect failed...", exc);
                }
            });
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
