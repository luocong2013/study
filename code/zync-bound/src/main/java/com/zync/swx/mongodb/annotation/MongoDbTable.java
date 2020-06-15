package com.zync.swx.mongodb.annotation;

import java.lang.annotation.*;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.mongodb.annotation
 * @description MongoDB表注释
 * @date 2017-12-5 14:53
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface MongoDbTable {
    String tableName() default "test";
}
