package com.zync.clone.domain;

/**
 * @author luocong
 * @description
 * @date 2020/5/25 14:34
 */
public class CloneDomain implements Cloneable {

    private int age;

    private String name;

    public CloneDomain() {
    }

    public CloneDomain(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
