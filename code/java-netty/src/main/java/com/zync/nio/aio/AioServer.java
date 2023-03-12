package com.zync.nio.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * 网络 AIO 服务端
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/11 23:38
 */
@Slf4j
public class AioServer {

    public static void main(String[] args) {
        try (AsynchronousServerSocketChannel ssc = AsynchronousServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(8000));
            ssc.accept(null, new AcceptHandler(ssc));
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {

        private final AsynchronousServerSocketChannel ssc;

        private AcceptHandler(AsynchronousServerSocketChannel ssc) {
            this.ssc = ssc;
        }

        @Override
        public void completed(AsynchronousSocketChannel sc, Object attachment) {
            try {
                log.debug("{} connected", sc.getRemoteAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteBuffer buffer = ByteBuffer.allocate(16);
            // 读事件由 ReadHandler 处理
            sc.read(buffer, buffer, new ReadHandler(sc));
            // 写事件由 WriteHandler 处理
            sc.write(StandardCharsets.UTF_8.encode("server hello!"), ByteBuffer.allocate(16), new WriteHandler(sc));
            // 处理完第一个 accept 时，需要再次调用 accept 方法来处理下一个 accept 事件
            ssc.accept(null, this);
        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            log.error("accept failed...", exc);
        }
    }

    private static class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

        private final AsynchronousSocketChannel sc;

        public ReadHandler(AsynchronousSocketChannel sc) {
            this.sc = sc;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            try {
                if (result == -1) {
                    closeChannel(sc);
                    return;
                }
                log.debug("{} read", sc.getRemoteAddress());
                attachment.flip();
                log.info(StandardCharsets.UTF_8.decode(attachment).toString());
                attachment.clear();
                // 处理完第一个 read 时，需要再次调用 read 方法来处理下一个 read 事件
                sc.read(attachment, attachment, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            log.error("read failed...", exc);
            closeChannel(sc);
        }
    }

    private static class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {

        private final AsynchronousSocketChannel sc;

        private WriteHandler(AsynchronousSocketChannel sc) {
            this.sc = sc;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            // 如果作为附件的 buffer 还有内容，需要再次 write 写出剩余内容
            if (attachment.hasRemaining()) {
                sc.write(attachment);
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            log.error("write failed...", exc);
            closeChannel(sc);
        }
    }

    private static void closeChannel(AsynchronousSocketChannel sc) {
        try {
            log.debug("{} close", sc.getRemoteAddress());
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
