package com.zync.dynamic.tests;

import com.zync.dynamic.aspect.SimpleAspect;
import com.zync.dynamic.proxy.SpringCglibProxy;

/**
 * spring cglib动态代理测试
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/29 10:42
 */
public class SpringCglibProxyTests {

    public static void main(String[] args) {
        // 将cglib生成的代理类写入到磁盘
        //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/luocong/Downloads");

        Cat cat = new Cat("花猫");
        Cat proxy = SpringCglibProxy.createProxy(cat, new SimpleAspect());
        proxy.setName("白猫");
        proxy.run(proxy.getName());
    }
}
