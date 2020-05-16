package com.zync.leetcode.order;

import java.util.*;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption
 *              找出 1 - 1000000 连续的数字中，被删除的数字
 *              1）BitSet 方式效率最高
 *              2）Map 次之
 * @date 2020/5/16 10:56
 */
public class Order {

    public static final int COUNT = 1000000;

    public static void main(String[] args){
        List<Integer> orders = new ArrayList<>(COUNT);
        for (int i = 1; i <= COUNT; i++) {
            orders.add(i);
        }
        orders.remove(Integer.valueOf(2));
        orders.remove(Integer.valueOf(102));
        orders.remove(Integer.valueOf(111102));

        findRemove(orders, COUNT);

        findRemoveByMap(orders, COUNT);
    }

    private static void findRemove(List<Integer> orders, int count) {
        long start = System.currentTimeMillis();
        List<Integer> removed = new ArrayList<>();

        BitSet bitSet = new BitSet(count);
        for (Integer order : orders) {
            bitSet.set(order);
        }

        for (int i = 1; i <= count; i++) {
            if (!bitSet.get(i)) {
                removed.add(i);
            }
        }

        for (Integer remove : removed) {
            System.out.println(remove);
        }

        long end = System.currentTimeMillis();
        System.out.println("BitSet : " + (end - start) + " ms");
    }

    private static void findRemoveByMap(List<Integer> orders, int count) {
        long start = System.currentTimeMillis();
        List<Integer> removed = new ArrayList<>();

        Map<Integer, Integer> map = new HashMap<>(count);
        for (Integer order : orders) {
            map.put(order, order);
        }

        for (int i = 1; i <= count; i++) {
            if (!map.containsKey(i)) {
                removed.add(i);
            }
        }

        for (Integer remove : removed) {
            System.out.println(remove);
        }

        long end = System.currentTimeMillis();
        System.out.println("map : " + (end - start) + " ms");
    }
}
