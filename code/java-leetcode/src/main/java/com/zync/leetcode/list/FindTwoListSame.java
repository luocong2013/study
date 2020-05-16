package com.zync.leetcode.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 查找两个list中相同的元素
 * @date 2020/5/16 15:16
 */
public class FindTwoListSame {

    private static final int COUNT = 100000;

    public static void main(String[] args){
        // 初始化数据
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            list1.add("list_" + i);
            list2.add("list_" + 3 * i);
        }

        //
        List<String> sameList = getSame2(list1, list2);
        System.out.println(sameList.size());

    }

    /**
     * 第一种方式，非常耗时，千万不能用，时间复杂读为 O(n^2)
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getSame1(List<String> list1, List<String> list2) {
        long start = System.currentTimeMillis();
        List<String> sames = new ArrayList<>();

        for (String str : list1) {
            if (list2.contains(str)) {
                sames.add(str);
            }
        }

        System.out.println("total times " + (System.currentTimeMillis() - start) + " ms");
        return sames;
    }

    private static List<String> getSame2(List<String> list1, List<String> list2) {
        long start = System.currentTimeMillis();
        List<String> sames = new ArrayList<>();

        List<String> maxList = list1;
        List<String> minList = list2;

        if (list2.size() > list1.size()) {
            maxList = list2;
            minList = list1;
        }

        Map<String, Integer> map = new HashMap<>(maxList.size());
        for (String str : maxList) {
            map.put(str, 1);
        }

        for (String str : minList) {
            if (map.containsKey(str)) {
                sames.add(str);
            }
        }

        System.out.println("total times " + (System.currentTimeMillis() - start) + " ms");
        return sames;
    }

}
