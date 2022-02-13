package com.zync.boot.redistools.aop;

import com.zync.boot.redistools.annotation.DistributedLock;
import com.zync.boot.redistools.common.Const;
import com.zync.boot.redistools.common.DistributedLockValueType;
import com.zync.boot.redistools.exception.RedisToolsServiceException;
import com.zync.boot.redistools.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodClassKey;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Core Interceptor of Distributed Lock
 *
 * @author luocong
 * @version v1.0
 * @date 2022/2/11 17:32
 */
@Slf4j
public class DistributedLockAnnotationInterceptor implements MethodInterceptor {

    private static final DistributedLock ANNOTATION_NULL = DistributedLockAnnotationInterceptor.class.getAnnotation(DistributedLock.class);

    private final Map<MethodClassKey, DistributedLock> distributedLockCache = new ConcurrentHashMap<>(16);

    private final RedisTemplate<String, String> redisTemplate;

    public DistributedLockAnnotationInterceptor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        DistributedLock lock = findAnnotation(invocation);
        if (Objects.isNull(lock)) {
            return invocation.proceed();
        }
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
                    return invocation.proceed();
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
     * 查找注解
     * @param invocation
     * @return
     */
    private DistributedLock findAnnotation(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        Object targetObject = invocation.getThis();
        Class<?> clazz = Objects.nonNull(targetObject) ? AopUtils.getTargetClass(targetObject) : method.getDeclaringClass();
        MethodClassKey cacheKey = new MethodClassKey(method, clazz);
        // 1. 从缓存中获取
        DistributedLock annotation = distributedLockCache.get(cacheKey);
        if (Objects.nonNull(annotation)) {
            return annotation != ANNOTATION_NULL ? annotation : null;
        }

        // 2. 从方法中获取
        annotation = AnnotationUtils.findAnnotation(method, DistributedLock.class);
        if (Objects.isNull(annotation)) {
            // 3. 从类上获取
            annotation = AnnotationUtils.findAnnotation(clazz, DistributedLock.class);
        }
        // 4. 添加到缓存
        distributedLockCache.put(cacheKey, Objects.nonNull(annotation) ? annotation : ANNOTATION_NULL);
        return annotation;
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
