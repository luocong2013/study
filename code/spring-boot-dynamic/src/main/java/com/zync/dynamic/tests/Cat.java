package com.zync.dynamic.tests;

/**
 * @author luocong
 * @version v2.2.1
 * @since 2024/4/28 19:45
 */
public class Cat {

    private String name;

    public Cat() {
    }

    public Cat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void run(String name) {
        System.out.println(name + " 用2条腿在跑");
    }
}
