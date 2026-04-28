package com.mountain.common.holder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redisson工具类，提供分布式锁、限流器等功能
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/28 15:09
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonHolder {

    private final RedissonClient redissonClient;

    /**
     * 获取字符串对象
     *
     * @param key 键
     * @return 字符串对象
     */
    public <V> RBucket<V> getBucket(String key) {
        return redissonClient.getBucket(key);
    }

    /**
     * 获取Map对象
     *
     * @param key 键
     * @return Map对象
     */
    public <K, V> RMap<K, V> getMap(String key) {
        return redissonClient.getMap(key);
    }

    /**
     * 获取有序集合对象
     *
     * @param key 键
     * @return 有序集合对象
     */
    public <V> RSortedSet<V> getSortedSet(String key) {
        return redissonClient.getSortedSet(key);
    }

    /**
     * 获取集合对象
     *
     * @param key 键
     * @return 集合对象
     */
    public <V> RSet<V> getSet(String key) {
        return redissonClient.getSet(key);
    }

    /**
     * 获取列表对象
     *
     * @param key 键
     * @return 列表对象
     */
    public <V> RList<V> getList(String key) {
        return redissonClient.getList(key);
    }

    /**
     * 获取队列对象
     *
     * @param key 键
     * @return 队列对象
     */
    public <V> RQueue<V> getQueue(String key) {
        return redissonClient.getQueue(key);
    }

    /**
     * 获取双端队列对象
     *
     * @param key 键
     * @return 双端队列对象
     */
    public <V> RDeque<V> getDeque(String key) {
        return redissonClient.getDeque(key);
    }

    /**
     * 获取锁对象
     *
     * @param key 键
     * @return 锁对象
     */
    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    /**
     * 获取读写锁对象
     *
     * @param key 键
     * @return 读写锁对象
     */
    public RReadWriteLock getReadWriteLock(String key) {
        return redissonClient.getReadWriteLock(key);
    }

    /**
     * 获取原子长整型对象
     *
     * @param key 键
     * @return 原子长整型对象
     */
    public RAtomicLong getAtomicLong(String key) {
        return redissonClient.getAtomicLong(key);
    }

    /**
     * 获取原子双精度浮点型对象
     *
     * @param key 键
     * @return 原子双精度浮点型对象
     */
    public RAtomicDouble getAtomicDouble(String key) {
        return redissonClient.getAtomicDouble(key);
    }

    /**
     * 获取计数器对象
     *
     * @param key 键
     * @return 计数器对象
     */
    public RCountDownLatch getCountDownLatch(String key) {
        return redissonClient.getCountDownLatch(key);
    }

    /**
     * 获取信号量对象
     *
     * @param key 键
     * @return 信号量对象
     */
    public RSemaphore getSemaphore(String key) {
        return redissonClient.getSemaphore(key);
    }

    /**
     * 获取限流器对象
     *
     * @param key 键
     * @return 限流器对象
     */
    public RRateLimiter getRateLimiter(String key) {
        return redissonClient.getRateLimiter(key);
    }

    // ============================= 分布式锁操作 ============================
    /**
     * 尝试获取锁
     *
     * @param lockKey   锁的key
     * @param waitTime  等待时间
     * @param leaseTime 持有锁的时间
     * @param timeUnit  时间单位
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit timeUnit) {
        RLock lock = getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            log.error("获取锁异常: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 尝试获取锁，不等待，无限时间默认 30 秒会自动续期，需要手动释放锁
     *
     * @param lockKey 锁的key
     * @return 是否获取成功
     */
    public boolean tryLockNoTime(String lockKey) {
        return tryLock(lockKey, 0, -1, TimeUnit.SECONDS);
    }

    /**
     * 尝试获取锁，默认等待时间为0，持有锁时间为30秒
     *
     * @param lockKey 锁的key
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey) {
        return tryLock(lockKey, 0, 30, TimeUnit.SECONDS);
    }

    /**
     * 释放锁
     *
     * @param lockKey 锁的key
     */
    public void unlock(String lockKey) {
        RLock lock = getLock(lockKey);
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    // ============================= 限流器操作 ============================
    /**
     * 尝试获取令牌
     *
     * @param key     限流器的key
     * @param permits 获取令牌数量
     * @return 是否获取成功
     */
    public boolean tryAcquire(String key, long permits) {
        RRateLimiter rateLimiter = getRateLimiter(key);
        return rateLimiter.tryAcquire(permits);
    }

}
