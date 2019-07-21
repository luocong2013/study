package com.zync.pool;

import com.zync.utils.StringKit;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 线程池
 * @date 2019/7/21 17:46
 */
public final class ThreadPoolHelper {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolHelper.class);

    /**
     * 核心线程数量
     */
    private static final int CORE_POOL_SIZE = 4;

    /**
     * 最大线程数量
     */
    private static final int MAX_POOL_SIZE = 8;

    /**
     * 线程池
     */
    private static ThreadPoolExecutor commitTaskPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            new BasicThreadFactory.Builder().namingPattern("example-pool-%d").daemon(false).build(), new ThreadPoolExecutor.CallerRunsPolicy());

    private ThreadPoolHelper () {
    }

    /**
     * 执行任务
     * @param commitTask 任务
     */
    public static void executeTask(Runnable commitTask) {
        commitTaskPool.execute(commitTask);
        logger.debug("【线程池任务】线程池中线程数：" + commitTaskPool.getPoolSize());
        logger.debug("【线程池任务】队列中等待执行的任务数：" + commitTaskPool.getQueue().size());
        logger.debug("【线程池任务】已执行完任务数：" + commitTaskPool.getCompletedTaskCount());
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        logger.debug("shutdown commitTaskPool...");
        commitTaskPool.shutdown();
        try {
            if (!commitTaskPool.isTerminated()) {
                logger.info("直接关闭失败【{}】", commitTaskPool.toString());
                commitTaskPool.awaitTermination(3, TimeUnit.SECONDS);
                if (commitTaskPool.isTerminated()) {
                    logger.info("关闭成功【{}】", commitTaskPool.toString());
                } else {
                    logger.info("【{}】关闭失败，执行shutdownNow关闭线程池...", commitTaskPool.toString());
                    if (commitTaskPool.shutdownNow().size() > 0) {
                        logger.info("关闭失败【{}】", commitTaskPool.toString());
                    } else {
                        logger.info("shutdownNow执行完毕，关闭成功【{}】", commitTaskPool.toString());
                    }
                }
            } else {
                logger.info("关闭成功【{}】", commitTaskPool.toString());
            }
        } catch (InterruptedException e) {
            logger.error(StringKit.format("接收中断请求异常【{}】", commitTaskPool.toString()), e);
        }
    }
}