package com.ccyw.lucene.annotation;

import java.lang.annotation.*;

/**
 * @author luoc
 * @version V1.0.0
 * @description Lucene索引库注释
 * @date 2018/10/27 22:37
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface LuceneIndex {
    String indexName() default "D:/lucene/test";
}
