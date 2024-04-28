package com.zync.dynamic.tests;

/**
 * 狗
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/28 19:10
 */
public class Dog implements Animal {

    private final String name;

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void run(String name) {
        System.out.println(name + " 用4条腿在跑");
    }
}
