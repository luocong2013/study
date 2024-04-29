package com.zync.dynamic.proxy;

import com.zync.dynamic.aspect.Aspect;
import com.zync.dynamic.interceptor.CglibInterceptor;
import net.sf.cglib.proxy.Enhancer;

/**
 * cglib动态代理
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/28 19:43
 */
public class CglibProxy {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, Aspect aspect) {
        // 1、声明增强类实例,用于生产代理类
        final Enhancer enhancer = new Enhancer();
        // 2、设置被代理类字节码，CGLIB根据字节码生成被代理类的子类
        enhancer.setSuperclass(target.getClass());
        // 3、设置回调函数，即一个方法拦截
        enhancer.setCallback(new CglibInterceptor(target, aspect));
        // 4、创建代理
        return (T) enhancer.create();
    }

}
