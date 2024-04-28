package com.zync.dynamic.proxy;

import com.zync.dynamic.aspect.Aspect;
import com.zync.dynamic.interceptor.JdkInterceptor;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/28 18:27
 */
public class JdkProxy {

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(T target, Aspect aspect) {
        Class<?> clazz = target.getClass();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new JdkInterceptor(target, aspect));
    }

}
