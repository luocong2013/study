package com.zync.dynamic.aspect;

import java.lang.reflect.Method;

/**
 * 简单的切面实现
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/28 19:04
 */
public class SimpleAspect implements Aspect {

    @Override
    public boolean before(Object target, Method method, Object[] args) {
        System.out.println("开始执行...");
        return true;
    }

    @Override
    public void after(Object target, Method method, Object[] args, Object returnVal) {
        System.out.println("结束执行...");
    }

    @Override
    public boolean afterException(Object target, Method method, Object[] args, Throwable e) {
        System.err.println("出异常了...");
        return true;
    }

}
