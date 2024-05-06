package com.zync.body.annotation;

import java.lang.annotation.*;

/**
 * 对响应Body编码
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/30 18:14
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EncodeBody {
}
