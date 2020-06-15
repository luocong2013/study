package com.zync.guava.cache.bean;

import lombok.Data;

import java.util.Comparator;

/**
 * @author luocong
 * @description
 * @date 2020/5/21 14:42
 */
@Data
public class Investor implements Comparator<Investor>, Comparable {
    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 父进程ID
     */
    private String parentPId;
    /**
     * 进程ID
     */
    private String PId;
    /**
     * 名字
     */
    private String name;


    @Override
    public int compare(Investor o1, Investor o2) {
        if (o1 == o2) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            return 1;
        } else {
            return o1.getName().compareTo(o2.getName());
        }
    }

    @Override
    public int compareTo(Object o) {
        Investor o1 = null;
        if (o instanceof Investor) {
            o1 = (Investor) o;
        }
        if (this == o) {
            return 0;
        } else if (o == null) {
            return 1;
        } else {
            return this.getName().compareTo(o1.getName());
        }
    }
}
