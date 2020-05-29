package com.redis.redisdemo.annotation;

import com.redis.redisdemo.common.LimitType;

import java.lang.annotation.*;

/**
 * @author luocong
 * @description 自定义限流注解
 * @date 2020/5/28 17:49
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limit {
    /**
     * 名字
     * @return
     */
    String name() default "";

    /**
     * key
     * @return
     */
    String key() default "";

    /**
     * key的前缀
     * @return
     */
    String prefix() default "";

    /**
     * 给定的时间范围 单位：秒
     * @return
     */
    int period();

    /**
     * 一定的时间内最多访问次数
     * @return
     */
    int count();

    /**
     * 限流的类型
     * @return
     */
    LimitType limitType() default LimitType.CUSTOMER;
}
