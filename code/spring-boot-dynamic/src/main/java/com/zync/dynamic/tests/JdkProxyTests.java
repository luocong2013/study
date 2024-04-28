package com.zync.dynamic.tests;

import com.zync.dynamic.aspect.SimpleAspect;
import com.zync.dynamic.proxy.JdkProxy;

/**
 * JDK动态代理测试
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/28 18:57
 */
public class JdkProxyTests {

    public static void main(String[] args) {
        Animal dog = new Dog("大黄");
        Animal animal = JdkProxy.getProxy(dog, new SimpleAspect());
        animal.run(animal.getName());
    }

}
