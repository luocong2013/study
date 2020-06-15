package com.zync.clone;

import com.zync.clone.domain.CloneDomain;

/**
 * @author luocong
 * @description
 * @date 2020/5/25 14:36
 */
public class CloneClient {

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneDomain domain = new CloneDomain(11, "张三");
        CloneDomain clone = (CloneDomain) domain.clone();

        System.out.println(domain.getName().getClass().getName() + "@" + Integer.toHexString(domain.getName().hashCode()));
        System.out.println(clone.getName().getClass().getName() + "@" + Integer.toHexString(clone.getName().hashCode()));
        System.out.println(domain.getName() == clone.getName() ? "浅拷贝" : "深拷贝");

        CloneDomain shallowCopy = BeanUtil.shallowCopy(domain, CloneDomain.class);
        System.out.println(shallowCopy.getName().getClass().getName() + "@" + Integer.toHexString(shallowCopy.getName().hashCode()));
        System.out.println(domain.getName() == shallowCopy.getName() ? "浅拷贝" : "深拷贝");


        CloneDomain copyProperties = cn.hutool.core.bean.BeanUtil.copyProperties(domain, CloneDomain.class);
        System.out.println(copyProperties.getName().getClass().getName() + "@" + Integer.toHexString(copyProperties.getName().hashCode()));
        System.out.println(domain.getName() == copyProperties.getName() ? "浅拷贝" : "深拷贝");
    }
}
