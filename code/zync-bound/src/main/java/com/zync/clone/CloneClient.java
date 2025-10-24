package com.zync.clone;

import com.zync.clone.domain.CloneDomain;
import com.zync.clone.domain.CloneDomainItem;

/**
 * @author luocong
 * @description
 * @date 2020/5/25 14:36
 */
public class CloneClient {

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneDomainItem domainItem = new CloneDomainItem("内部-李四");
        CloneDomain domain = new CloneDomain(11, "张三", domainItem);
        String domainNameClass = domain.getCloneDomainItem().getClass().getName() + "@" + Integer.toHexString(domain.getCloneDomainItem().hashCode());
        System.out.println(domainNameClass);

        CloneDomain clone = (CloneDomain) domain.clone();
        String cloneNameClass = clone.getCloneDomainItem().getClass().getName() + "@" + Integer.toHexString(clone.getCloneDomainItem().hashCode());
        System.out.println(cloneNameClass);
        System.out.println(domainNameClass.equals(cloneNameClass) ? "浅拷贝" : "深拷贝");

        CloneDomain shallowCopy = BeanUtil.shallowCopy(domain, CloneDomain.class);
        String shallowCopyNameClass = shallowCopy.getCloneDomainItem().getClass().getName() + "@" + Integer.toHexString(shallowCopy.getCloneDomainItem().hashCode());
        System.out.println(shallowCopyNameClass);
        System.out.println(domainNameClass.equals(shallowCopyNameClass) ? "浅拷贝" : "深拷贝");


        CloneDomain copyProperties = cn.hutool.core.bean.BeanUtil.copyProperties(domain, CloneDomain.class);
        String copyPropertiesNameClass = copyProperties.getCloneDomainItem().getClass().getName() + "@" + Integer.toHexString(copyProperties.getCloneDomainItem().hashCode());
        System.out.println(copyPropertiesNameClass);
        System.out.println(domainNameClass.equals(copyPropertiesNameClass) ? "浅拷贝" : "深拷贝");
    }
}
