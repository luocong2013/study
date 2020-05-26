package com.redis.redisdemo.task;

import com.redis.redisdemo.lock.RedisLock;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @description 任务管理器
 * @date 2020/5/26 13:52
 */
@Component
public class TaskManager implements InitializingBean {

    /**
     * 核心线程数量
     */
    private static final int CORE_POOL_SIZE = 4;
    /**
     * 最大线程数量
     */
    private static final int MAX_POOL_SIZE = 8;

    private static final ThreadPoolExecutor commitTaskPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            new BasicThreadFactory.Builder().namingPattern("example-pool-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());

    private final RedisLock redisLock;

    public TaskManager(RedisLock redisLock) {
        this.redisLock = redisLock;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        commitTaskPool.execute(new Task1(redisLock));
        commitTaskPool.execute(new Task2(redisLock));
    }
}
