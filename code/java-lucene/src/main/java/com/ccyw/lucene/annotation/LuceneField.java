package com.ccyw.lucene.annotation;

import java.lang.annotation.*;

/**
 * @author luoc
 * @version V1.0.0
 * @description Lucene Field域字段
 * @date 2018/10/27 22:40
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface LuceneField {
    String fieldName() default "";
}
