package com.zync.boot.redistools.aspect;

import com.zync.boot.redistools.annotation.DistributedLock;
import com.zync.boot.redistools.common.Const;
import com.zync.boot.redistools.common.DistributedLockValueType;
import com.zync.boot.redistools.exception.RedisToolsServiceException;
import com.zync.boot.redistools.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @version V1.0.0
 * @descrption 分布式锁切面
 * @date 2020/9/19 15:03
 */
@Slf4j
@Aspect
public class DistributedLockAspect {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 切点
     */
    @Pointcut(value = "@annotation(com.zync.boot.redistools.annotation.DistributedLock)")
    public void pointcut() {
    }

    /**
     * 环绕通知
     * @param point ProceedingJoinPoint
     * @param lock  {@link DistributedLock}
     * @return
     */
    @Around(value = "pointcut() && @annotation(lock)", argNames = "point, lock")
    public Object around(ProceedingJoinPoint point, DistributedLock lock) {
        // 功能名称
        String name = lock.name();
        // 分布式锁的key
        String key = lock.key();
        // 分布式锁key对应的值的类型
        DistributedLockValueType valueType = lock.valueType();
        // 分布式锁过期时间
        long timeout = lock.timeout();
        // 分布式锁过期时间单位
        TimeUnit unit = lock.unit();
        // 获取锁的次数
        int count = lock.count();
        // 重复抢锁等待时间间隔 (单位: 毫秒)
        long timeInterval = lock.timeInterval();

        // 根据不同的值类型，来取值
        String value;
        switch (valueType) {
            case CUSTOMER:
                value = lock.value();
                break;
            case UUID:
                value = StringUtil.uuid();
                break;
            default:
                value = StringUtil.snowflake();
        }
        log.info("get redis lock, name is {} key is {} value is {} timeout is {} count is {}", name, key, value, timeout, count);

        try {
            do {
                if (Boolean.TRUE.equals(acquire(name, key, value, timeout, unit))) {
                    // 执行目标方法
                    return point.proceed();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(timeInterval);
                } catch (InterruptedException e) {
                    // 设置中断状态
                    Thread.currentThread().interrupt();
                    log.error("线程被中断", e);
                }
            } while (count <= 0 || --count > 0);
            throw new RedisToolsServiceException("Your operation is too frequent, please wait two seconds and try again.");
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RedisToolsServiceException(e.getMessage(), e);
            }
            throw new RuntimeException(e);
        } finally {
            // 释放锁
            release(key, value);
        }
    }

    /**
     * 获取锁
     * @param name    功能名称
     * @param key     锁的key
     * @param value   锁的value
     * @param timeout 锁过期时间
     * @param unit    锁过期时间单位
     * @return {@code true} if get lock successful or {@code false} if not or error
     */
    private Boolean acquire(String name, String key, String value, long timeout, TimeUnit unit) {
        try {
            return redisTemplate.boundValueOps(key).setIfAbsent(value, timeout, unit);
        } catch (Exception e) {
            log.error(StringUtil.format("Name is {}, get redis lock error", name), e);
        }
        return Boolean.FALSE;
    }

    /**
     * 释放锁
     * @param key   锁的key
     * @param value 锁的value
     * @return {@code true} if release the lock successful or {@code false} if not or error
     */
    private Boolean release(String key, String value) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.eval(Const.LUA_RELEASE_LOCK.getBytes(), ReturnType.BOOLEAN, 1, key.getBytes(), value.getBytes()));
    }
}
