package com.zync.dynamic.interceptor;

import com.zync.dynamic.aspect.Aspect;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * cglib实现的动态代理
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/28 19:30
 */
public class CglibInterceptor implements MethodInterceptor {

    private final Object target;
    private final Aspect aspect;

    /**
     * 构造方法
     * @param target 被代理对象
     * @param aspect 切面实现类
     */
    public CglibInterceptor(Object target, Aspect aspect) {
        this.target = target;
        this.aspect = aspect;
    }

    /**
     *
     * @param proxy 生成的代理对象, 名称类似这种: com.zync.dynamic.tests.Cat$$EnhancerByCGLIB$$74519f57
     * @param method
     * @param args
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = null;

        // 开始前的回调
        if (aspect.before(target, method, args)) {
            try {
                // 代理对象调用父类的方法（实际调用的是 CGLIB$method$index 方法，这个方法直接调用了 supper.method 方法，且 obj 只能传入动态代理的对象 ）
                //result = methodProxy.invokeSuper(proxy, args);
                // 调用原对象的 method 方法
                result = methodProxy.invoke(target, args);
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
