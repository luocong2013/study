package com.zync.clone.domain;

/**
 * @author luocong
 * @description
 * @date 2020/5/25 14:34
 */
public class CloneDomain implements Cloneable {

    private int age;

    private String name;

    private CloneDomainItem cloneDomainItem;

    public CloneDomain() {
    }

    public CloneDomain(int age, String name, CloneDomainItem cloneDomainItem) {
        this.age = age;
        this.name = name;
        this.cloneDomainItem = cloneDomainItem;
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

    public CloneDomainItem getCloneDomainItem() {
        return cloneDomainItem;
    }

    public void setCloneDomainItem(CloneDomainItem cloneDomainItem) {
        this.cloneDomainItem = cloneDomainItem;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
