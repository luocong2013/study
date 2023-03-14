package com.zync.netty.future;

import com.zync.nio.ch5.MultiThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * jdk future 使用
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/14 12:56
 */
@Slf4j
public class JdkFuture {

    private static final ThreadFactory THREAD_FACTORY = new MultiThreadFactory("JdkFuture-");

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), THREAD_FACTORY);
        // 获得 Future 对象
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("开始计算");
                TimeUnit.SECONDS.sleep(1);
                return 50;
            }
        });

        log.debug("等待结果...");
        // 通过阻塞方式 获得结果
        log.debug("结果为: {}", future.get());
    }
}
