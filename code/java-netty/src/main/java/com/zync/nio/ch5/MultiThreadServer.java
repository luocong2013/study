package com.zync.nio.ch5;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static com.zync.nio.utils.ByteBufferUtil.debugAll;

/**
 * nio selector模式 服务端（多线程优化）
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/11 18:33
 */
@Slf4j
public class MultiThreadServer {

    private static final ThreadFactory BOSS_THREAD_FACTORY = new MultiThreadFactory("boss-");
    private static final ThreadFactory WORKER_THREAD_FACTORY = new MultiThreadFactory("worker-");

    public static void main(String[] args) throws IOException {
        new BossEventLoop().register();
    }

    @Slf4j
    private static class BossEventLoop implements Runnable {
        private Selector boss;
        private WorkerEventLoop[] workers;
        private volatile boolean start = false;
        AtomicInteger index = new AtomicInteger();

        /**
         * 初始化 selector 和 启动 boss
         * @throws IOException
         */
        public void register() throws IOException {
            if (!this.start) {
                ServerSocketChannel ssc = ServerSocketChannel.open();
                ssc.configureBlocking(false);
                ssc.bind(new InetSocketAddress(8000));
                this.boss = Selector.open();
                ssc.register(this.boss, SelectionKey.OP_ACCEPT);
                this.workers = initEventLoops();
                BOSS_THREAD_FACTORY.newThread(this).start();
                this.start = true;
                log.debug("boss started...");
            }
        }

        /**
         * 初始化 WorkerEventLoop 数组
         * @return
         */
        private WorkerEventLoop[] initEventLoops() {
            WorkerEventLoop[] loops = new WorkerEventLoop[2];
            for (int i = 0; i < loops.length; i++) {
                loops[i] = new WorkerEventLoop();
            }
            return loops;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    this.boss.select();
                    Iterator<SelectionKey> iterator = this.boss.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                            SocketChannel sc = ssc.accept();
                            sc.configureBlocking(false);
                            log.debug("connected...{}", sc.getRemoteAddress());
                            // 2. 关联 selector
                            log.debug("before register...{}", sc.getRemoteAddress());
                            // 轮询
                            this.workers[this.index.getAndIncrement() % this.workers.length].register(sc);
                            log.debug("after register...{}", sc.getRemoteAddress());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Slf4j
    private static class WorkerEventLoop implements Runnable {
        private Selector worker;
        private volatile boolean start = false;

        /**
         * 初始化 线程 和 selector
         */
        public void register(SocketChannel sc) throws IOException {
            if (!this.start) {
                this.worker = Selector.open();
                WORKER_THREAD_FACTORY.newThread(this).start();
                this.start = true;
            }
            // 唤醒 selector
            this.worker.wakeup();
            sc.register(this.worker, SelectionKey.OP_READ, null);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // worker 线程阻塞, wakeup 方法唤醒
                    this.worker.select();
                    Iterator<SelectionKey> iterator = this.worker.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            log.debug("read...{}", sc.getRemoteAddress());
                            try {
                                int read = sc.read(buffer);
                                if (read == -1) {
                                    key.cancel();
                                    sc.close();
                                } else {
                                    buffer.flip();
                                    debugAll(buffer);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                key.cancel();
                                sc.close();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
