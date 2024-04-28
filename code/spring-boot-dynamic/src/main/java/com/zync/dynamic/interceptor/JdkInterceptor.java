package com.zync.dynamic.interceptor;

import com.zync.dynamic.aspect.Aspect;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * JDK实现的动态代理
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/28 18:17
 */
public class JdkInterceptor implements InvocationHandler, Serializable {

    @Serial
    private static final long serialVersionUID = -4031801402088482278L;

    private final Object target;
    private final Aspect aspect;

    public JdkInterceptor(Object target, Aspect aspect) {
        this.target = target;
        this.aspect = aspect;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Object target = this.target;
        final Aspect aspect = this.aspect;
        Object result = null;

        // 开始前的回调
        if (aspect.before(target, method, args)) {
            try {
                method.setAccessible(true);
                result = method.invoke(target, args);
            } catch (InvocationTargetException e) {
                // 异常回调（只捕获业务代码导致的异常，而非反射导致的异常）
                if (aspect.afterException(target, method, args, e)) {
                    throw e;
                }
            }
        }

        // 结束前的回调
        aspect.after(target, method, args, result);
        return result;
    }
}
