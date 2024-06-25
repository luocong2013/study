package com.zync.boot.mybatisplus.ext.core.annotation;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.lang.annotation.*;

/**
 * mybatis plus 使用 InsertBatchSomeColumn 批量插入时的注解，获取字段插入的默认值
 *
 * <p>
 * <pre>
 *  &#064;Target({ElementType.METHOD, ElementType.TYPE}) //CONSTRUCTOR: 构造方法声明、FIELD: 字段声明、LOCAL_VARIABLE: 局部变量声明、METHOD: 方法声明、PACKAGE: 包声明、PARAMETER: 参数声明、TYPE: 类接口
 *  &#064;Retention(RetentionPolicy.RUNTIME) //生命周期：SOURCE: 只在源码显示，编译时会丢弃、CLASS: 编译时会记录到class中，运行时忽略、RUNTIME: 运行时存在，可以通过反射读取
 *  &#064;Inherited //允许子类继承
 *  &#064;Documented //生成javadoc时会包含注解
 * </pre>
 * @author luocong
 * @version v1.0
 * @date 2022/1/28 09:55
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Default {

    /**
     * byte 默认值
     */
    byte byteValue() default -1;

    /**
     * short 默认值
     */
    short shortValue() default -1;

    /**
     * char 默认值
     */
    char charValue() default ' ';

    /**
     * int 默认值
     */
    int intValue() default -1;

    /**
     * long 默认值
     */
    long longValue() default -1L;

    /**
     * float 默认值
     */
    float floatValue() default -1F;

    /**
     * double 默认值
     */
    double doubleValue() default -1D;

    /**
     * boolean 默认值
     */
    boolean booleanValue() default false;

    /**
     * String 默认值
     */
    String stringValue() default StringPool.EMPTY;

    /**
     * Class 默认值
     */
    Class<?> classValue() default Class.class;
}
