package com.zync.guava.cache;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.TreeBasedTable;
import com.zync.guava.cache.bean.EdrProcess;

import java.util.Map;
import java.util.SortedMap;

/**
 * @author luocong
 * @description 自定义树
 * @date 2020/5/21 13:50
 */
public class CustomTree {

    /**
     * clientId -> pid ->
     * @param args
     */
    public static void main(String[] args) {
        HashBasedTable<String, String, EdrProcess> hashBasedTable = HashBasedTable.create();
        hashBasedTable.put("client01", "p1", new EdrProcess("进程1"));
        hashBasedTable.put("client01", "p2", new EdrProcess("进程2"));
        hashBasedTable.put("client01", "p3", new EdrProcess("进程3"));

        hashBasedTable.put("client02", "p1", new EdrProcess("client02进程1"));
        hashBasedTable.put("client02", "p2", new EdrProcess("client02进程2"));

        Map<String, EdrProcess> map = hashBasedTable.row("client01");
        EdrProcess p = map.get("p1");
        System.out.println(p.getProcessName());

        TreeBasedTable<String, String, EdrProcess> treeBasedTable = TreeBasedTable.create();
        treeBasedTable.put("client01", "p1", new EdrProcess("进程1"));
        treeBasedTable.put("client01", "p2", new EdrProcess("进程2"));
        treeBasedTable.put("client01", "p3", new EdrProcess("进程3"));

        treeBasedTable.put("client02", "p1", new EdrProcess("client02进程1"));
        treeBasedTable.put("client02", "p2", new EdrProcess("client02进程2"));

        SortedMap<String, EdrProcess> sortedMap = treeBasedTable.row("client01");
        EdrProcess pp = sortedMap.get("p1");
        System.out.println(pp.getProcessName());
    }
}
