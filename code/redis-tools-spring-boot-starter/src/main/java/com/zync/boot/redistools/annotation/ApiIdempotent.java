package com.zync.boot.redistools.annotation;

import java.lang.annotation.*;

/**
 * 在需要保证接口幂等性的Controller的方法上加上此注解
 *
 * @author luocong
 * @version v1.0.2
 * @date 2023/2/6 12:52
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {
}
