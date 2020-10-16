package com.zync.boot.redistools.annotation;

import com.zync.boot.redistools.common.Const;
import com.zync.boot.redistools.common.DistributedLockValueType;
import com.zync.boot.redistools.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author luocong
 * @version V1.0.0
 * @descrption 分布式锁注解
 * @date 2020/9/19 14:27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    /**
     * @return 功能名称
     */
    String name() default Const.DISTRIBUTED_LOCK_NAME;

    /**
     * @return 分布式锁的key
     */
    String key() default Const.DISTRIBUTED_LOCK_KEY;

    /**
     * @return 分布式锁key对应的自定义值 {@link DistributedLockValueType#CUSTOMER}
     */
    String value() default StringUtils.EMPTY;

    /**
     * @return 分布式锁key对应的值的类型, 默认值: 雪花算法生成的分布式ID {@link StringUtil#snowflake()}
     */
    DistributedLockValueType valueType() default DistributedLockValueType.SNOWFLAKE;

    /**
     * @return 分布式锁过期时间
     */
    long timeout() default Const.DISTRIBUTED_LOCK_KEY_TIMEOUT;

    /**
     * @return 分布式锁过期时间单位, 默认值: {@link TimeUnit#SECONDS} (秒)
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * @return 获取锁的次数, 默认值: {@code 0}
     *         值<= 0: 表示一直循环，直到获取到锁
     *         值>0: 表示获取指定的次数，获取不到就返回
     */
    int count() default 0;

    /**
     * @return 重复抢锁等待时间间隔 (单位: 毫秒)
     */
    long timeInterval() default Const.DISTRIBUTED_LOCK_TIME_INTERVAL;
}
