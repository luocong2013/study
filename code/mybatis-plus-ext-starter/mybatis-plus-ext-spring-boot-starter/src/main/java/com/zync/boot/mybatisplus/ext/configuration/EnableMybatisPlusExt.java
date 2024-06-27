package com.zync.boot.mybatisplus.ext.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * mybatis plus ext 启动注解
 *
 * @author luocong
 * @version v1.0
 * @since 2024/6/27 15:50
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MybatisPlusExtConfiguration.class)
public @interface EnableMybatisPlusExt {
}
