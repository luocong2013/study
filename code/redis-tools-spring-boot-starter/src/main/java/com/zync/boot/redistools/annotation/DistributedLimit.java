package com.zync.boot.redistools.annotation;

import com.zync.boot.redistools.common.Const;
import com.zync.boot.redistools.common.DistributedLimitType;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 分布式限流注解
 * @date 2020/9/20 15:37
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLimit {
    /**
     * 名字
     * @return
     */
    String name() default Const.DISTRIBUTED_LIMIT_NAME;

    /**
     * 限流key
     * @return
     */
    String key() default StringUtils.EMPTY;

    /**
     * 给定的时间范围（单位：秒）
     * @return
     */
    long period() default Const.DISTRIBUTED_LIMIT_PERIOD;

    /**
     * 给定时间范围内可以访问的次数
     * @return
     */
    long count() default Const.DISTRIBUTED_LIMIT_COUNT;

    /**
     * 限流类型
     * @return
     */
    DistributedLimitType limitType() default DistributedLimitType.CUSTOMER;
}
