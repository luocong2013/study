package com.zync.dynamic.tests;

/**
 * 动物接口类
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/28 19:08
 */
public interface Animal {

    /**
     * 动物名字
     * @return
     */
    String getName();

    /**
     * 跑
     * @param name
     */
    void run(String name);

}
