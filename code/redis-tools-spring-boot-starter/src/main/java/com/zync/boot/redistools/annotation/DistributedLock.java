package com.zync.boot.redistools.annotation;

import com.zync.boot.redistools.common.Const;
import com.zync.boot.redistools.common.DistributedLockValueType;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 分布式锁注解
 * @date 2020/9/19 14:27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    /**
     * 功能名称
     * @return
     */
    String name() default Const.DISTRIBUTED_LOCK_NAME;

    /**
     * 分布式锁的key
     * @return
     */
    String key() default Const.DISTRIBUTED_LOCK_KEY;

    /**
     * 分布式锁key对应的自定义值
     * @return
     */
    String value() default StringUtils.EMPTY;

    /**
     * 分布式锁key对应的值的类型（默认：雪花算法生成的分布式ID）
     * @return
     */
    DistributedLockValueType valueType() default DistributedLockValueType.SNOWFLAKE;

    /**
     * 分布式锁过期时间
     * @return
     */
    long timeout() default Const.DISTRIBUTED_LOCK_KEY_TIMEOUT;

    /**
     * 分布式锁过期时间单位（默认：秒）
     * @return
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
