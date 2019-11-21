package com.zync.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption AIO服务端
 * @date 2019/11/21 22:25
 */
public class AIOEchoServer {

    private static final int PORT = 8000;

    private AsynchronousServerSocketChannel server;

    public AIOEchoServer() throws IOException {
        this.server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
    }

    public void start() {
        System.out.println("Server listen on " + PORT);
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                System.out.println(Thread.currentThread().getName());
                Future<Integer> writeResult = null;
                try {
                    buffer.clear();
                    result.read(buffer).get(100, TimeUnit.SECONDS);
                    buffer.flip();
                    writeResult = result.write(buffer);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        server.accept(null, this);
                        writeResult.get();
                        result.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("failed: " + exc);
            }
        });
    }
}
