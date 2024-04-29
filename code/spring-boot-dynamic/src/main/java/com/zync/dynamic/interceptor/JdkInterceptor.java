package com.zync.dynamic.interceptor;

import com.zync.dynamic.aspect.Aspect;

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
public class JdkInterceptor implements InvocationHandler {

    private final Object target;
    private final Aspect aspect;

    /**
     * 构造方法
     * @param target 被代理对象
     * @param aspect 切面实现类
     */
    public JdkInterceptor(Object target, Aspect aspect) {
        this.target = target;
        this.aspect = aspect;
    }

    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with.
     * @param proxy the proxy instance that the method was invoked on
     *              （即: 生成的代理对象, 名称类似这种: jdk.proxy1.$Proxy0 ）
     *
     * @param method the {@code Method} instance corresponding to
     * the interface method invoked on the proxy instance.  The declaring
     * class of the {@code Method} object will be the interface that
     * the method was declared in, which may be a superinterface of the
     * proxy interface that the proxy class inherits the method through.
     *
     * @param args an array of objects containing the values of the
     * arguments passed in the method invocation on the proxy instance,
     * or {@code null} if interface method takes no arguments.
     * Arguments of primitive types are wrapped in instances of the
     * appropriate primitive wrapper class, such as
     * {@code java.lang.Integer} or {@code java.lang.Boolean}.
     *
     * @return object
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
