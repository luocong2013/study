package com.zync.leetcode.reverse;

import java.util.ArrayList;
import java.util.List;

/**
 * @user luoc
 * @date 2020/2/16 18:13
 * @description 杨辉三角
 **/
public class LeetCode_Yhsj {
    public static void main(String[] args) {
        LeetCode_Yhsj yhsj = new LeetCode_Yhsj();
        List<List<Integer>> lists = yhsj.generate(5);
        lists.forEach(item -> {
            item.forEach(System.out::print);
            System.out.println();
        });
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> results = new ArrayList<>();
        generateNum(results, 0, numRows);
        return results;
    }

    private void generateNum(List<List<Integer>> results, int i, int numRows) {
        if (i > numRows - 1) {
            return;
        }
        List<Integer> rows = new ArrayList<>();
        results.add(rows);
        for (int j = 0; j <= i; j++) {
            rows.add(j, generateInt(results, i, j));
        }

        generateNum(results, i + 1, numRows);
    }

    private Integer generateInt(List<List<Integer>> results, int i, int j) {
        if (j == 0) {
            return 1;
        }
        if (i == j) {
            return 1;
        }
        return results.get(i - 1).get(j - 1) + results.get(i - 1).get(j);
    }
}
