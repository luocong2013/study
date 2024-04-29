package com.zync.dynamic.interceptor;

import com.zync.dynamic.aspect.Aspect;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * spring cglib实现的动态代理
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/29 10:38
 */
public class SpringCglibInterceptor implements MethodInterceptor {

    private final Object target;
    private final Aspect aspect;

    /**
     * 构造方法
     * @param target 被代理对象
     * @param aspect 切面实现类
     */
    public SpringCglibInterceptor(Object target, Aspect aspect) {
        this.target = target;
        this.aspect = aspect;
    }

    /**
     *
     * @param obj "this", the enhanced object（即: 生成的代理对象, 名称类似这种: com.zync.dynamic.tests.Cat$$EnhancerByCGLIB$$e43f934）
     * @param method intercepted Method
     * @param args argument array; primitive types are wrapped
     * @param proxy used to invoke super (non-intercepted method); may be called
     * as many times as needed
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = null;

        // 开始前的回调
        if (aspect.before(target, method, args)) {
            try {
                // 代理对象调用父类的方法（实际调用的是 CGLIB$method$index 方法，这个方法直接调用了 supper.method 方法，且 obj 只能传入动态代理的对象 ）
                //result = proxy.invokeSuper(obj, args);
                // 调用原对象的 method 方法
                result = proxy.invoke(target, args);
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
