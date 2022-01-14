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
        String domainNameClass = domain.getName().getClass().getName() + "@" + Integer.toHexString(domain.getName().hashCode());
        System.out.println(domainNameClass);

        CloneDomain clone = (CloneDomain) domain.clone();
        String cloneNameClass = clone.getName().getClass().getName() + "@" + Integer.toHexString(clone.getName().hashCode());
        System.out.println(cloneNameClass);
        System.out.println(domainNameClass.equals(cloneNameClass) ? "浅拷贝" : "深拷贝");

        CloneDomain shallowCopy = BeanUtil.shallowCopy(domain, CloneDomain.class);
        String shallowCopyNameClass = shallowCopy.getName().getClass().getName() + "@" + Integer.toHexString(shallowCopy.getName().hashCode());
        System.out.println(shallowCopyNameClass);
        System.out.println(domainNameClass.equals(shallowCopyNameClass) ? "浅拷贝" : "深拷贝");


        CloneDomain copyProperties = cn.hutool.core.bean.BeanUtil.copyProperties(domain, CloneDomain.class);
        String copyPropertiesNameClass = copyProperties.getName().getClass().getName() + "@" + Integer.toHexString(copyProperties.getName().hashCode());
        System.out.println(copyPropertiesNameClass);
        System.out.println(domainNameClass.equals(copyPropertiesNameClass) ? "浅拷贝" : "深拷贝");
    }
}
