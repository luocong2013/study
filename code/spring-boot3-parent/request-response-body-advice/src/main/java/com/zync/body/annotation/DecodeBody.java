package com.zync.body.annotation;

import java.lang.annotation.*;

/**
 * 对请求解码
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/29 18:09
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface DecodeBody {
}
