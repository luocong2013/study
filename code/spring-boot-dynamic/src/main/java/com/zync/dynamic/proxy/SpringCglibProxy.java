package com.zync.dynamic.proxy;

import com.zync.dynamic.aspect.Aspect;
import com.zync.dynamic.interceptor.SpringCglibInterceptor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * spring cglib动态代理
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/29 10:40
 */
public class SpringCglibProxy {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, Aspect aspect) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new SpringCglibInterceptor(target, aspect));
        return (T) enhancer.create();
    }

}
