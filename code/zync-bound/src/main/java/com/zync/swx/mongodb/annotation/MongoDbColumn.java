package com.zync.swx.mongodb.annotation;

import java.lang.annotation.*;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.mongodb.annotation
 * @description MongoDB字段注解
 * @date 2017-12-5 14:56
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface MongoDbColumn {
    String columnName() default "";
}
