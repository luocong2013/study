package com.zync.guava.cache.bean;

import lombok.Data;

import java.util.List;

/**
 * @author luocong
 * @description
 * @date 2020/5/21 15:03
 */
@Data
public class EdrProcess {

    private EdrProcess parent;

    private List<EdrProcess> childs;

    private String pid;

    private String processName;

    public EdrProcess(String processName) {
        this.processName = processName;
    }
}
