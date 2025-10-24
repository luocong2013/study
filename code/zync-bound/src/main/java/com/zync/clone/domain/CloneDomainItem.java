package com.zync.clone.domain;

/**
 * @author luocong
 * @version 1.0
 * @description
 * @since 2025/10/24 16:51
 **/
public class CloneDomainItem {

    private String name;

    public CloneDomainItem() {
    }

    public CloneDomainItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
