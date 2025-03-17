package com.zync.leetcode.interview;

import java.util.Arrays;

/**
 * @author admin
 * @version 1.0
 * @description 合并数组，招银网络科技笔试题
 * @since 2025/3/16 10:45
 **/
public class MergeArray {

    public static void main(String[] args) {
        String[] strings = mergeArray(new String[]{"abc", "ab"}, new String[]{"a", "abcd"});

        System.out.println(Arrays.toString(strings));
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param first  string字符串一维数组 数组一
     * @param second string字符串一维数组 数组二
     * @return string字符串一维数组
     */
    public static String[] mergeArray(String[] first, String[] second) {
        int k = 0;
        String[] temp = new String[first.length + second.length];
        for (String s : first) {
            temp[k++] = s;
        }
        for (String s : second) {
            temp[k++] = s;
        }

        for (int i = 0; i < first.length + second.length - 1; i++) {
            for (int j = 0; j < first.length + second.length - 1 - i; j++) {
                if (temp[j].length() > temp[j + 1].length()) {
                    String tm = temp[j];
                    temp[j] = temp[j + 1];
                    temp[j + 1] = tm;
                }
            }
        }

        return temp;
    }
}
