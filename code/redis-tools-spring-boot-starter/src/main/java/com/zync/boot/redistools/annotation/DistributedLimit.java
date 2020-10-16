package com.zync.boot.redistools.annotation;

import com.zync.boot.redistools.common.Const;
import com.zync.boot.redistools.common.DistributedLimitKeyType;
import com.zync.boot.redistools.common.DistributedLimitType;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * @author luocong
 * @version V1.0.0
 * @descrption 分布式限流注解
 * @date 2020/9/20 15:37
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLimit {
    /**
     * @return 功能名称
     */
    String name() default Const.DISTRIBUTED_LIMIT_NAME;

    /**
     * @return 自定义限流key {@link DistributedLimitKeyType#CUSTOMER}
     */
    String key() default StringUtils.EMPTY;

    /**
     * @return 给定的时间范围（单位：秒）
     */
    long period() default Const.DISTRIBUTED_LIMIT_PERIOD;

    /**
     * @return 给定时间范围内可以访问的次数
     */
    long count() default Const.DISTRIBUTED_LIMIT_COUNT;

    /**
     * @return 限流key的类型, 默认值: 请求ip
     */
    DistributedLimitKeyType keyType() default DistributedLimitKeyType.IP;

    /**
     * @return 分布式限流类型, 默认值: 基于时间窗口限流
     */
    DistributedLimitType limitType() default DistributedLimitType.TIME;
}
