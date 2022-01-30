package com.zync.boot.mybatisplus.ext.annotation;

import java.lang.annotation.*;

/**
 * mybatis plus 使用 InsertBatchSomeColumn 批量插入时的注解，忽略插入该字段
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/30 15:22
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IgnoreInsert {
}
