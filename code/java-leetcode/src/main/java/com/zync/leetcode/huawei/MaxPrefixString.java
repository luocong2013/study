package com.zync.leetcode.huawei;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @user luoc
 * @date 2020/5/17 14:21
 * @description 最大前缀
 **/
public class MaxPrefixString {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int lineCount = Integer.parseInt(input.nextLine());
        String[] datas = new String[lineCount];
        for (int i = 0; i < lineCount; i++) {
            datas[i] = input.nextLine();
        }

        System.out.println(findMaxPrefix(datas, lineCount));

    }

    private static String findMaxPrefix(String[] strs, int lineCount) {
        Map<String, Integer> map = new HashMap<>(16);
        for (int i = 0; i < lineCount; i++) {
            String str = strs[i];
            for (int k = 1; k < str.length(); k++) {
                String substr = str.substring(0, k);
                int count = 1;
                if (map.containsKey(substr)) {
                    count = map.get(substr);
                    count++;
                }
                map.put(substr, count);
            }
        }

        String result = "";
        int maxLength = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value == lineCount) {
                if (key.length() > maxLength) {
                    maxLength = key.length();
                    result = key;
                }
            }
        }
        return result;
    }
}
