package com.zync.leetcode.bytedance;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 特征提取
 **/
public class Main2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int max = 0;
        Map<String, Integer> map = new HashMap<>(16);
        Map<String, Integer> temp = new HashMap<>(16);
        for (int i = 1; i <= n; i++) {
            int m = scanner.nextInt();
            scanner.nextLine();
            for (int k = 1; k <= m; k++) {
                map.clear();
                int count = scanner.nextInt();
                for (int h = 0; h < count; h++) {
                    String key = scanner.nextInt() + "_" + scanner.nextInt();
                    map.put(key, temp.getOrDefault(key, 0) + 1);
                    max = Math.max(map.get(key), max);
                }
                scanner.nextLine();
                temp.clear();
                temp.putAll(map);
            }
        }
        System.out.println(max);
    }
}
