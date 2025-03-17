package com.zync.leetcode.interview;

/**
 * @author admin
 * @version 1.0
 * @description 回文字符串（忽略空格和大小写）
 * @since 2025/3/17 18:56
 **/
public class Palindrome {

    public static void main(String[] args) {
        System.out.println(isPalindrome("a b Acaba"));
    }

    public static boolean isPalindrome(String str) {

        StringBuilder builder = new StringBuilder();
        String[] arrays = str.split("");
        for (String array : arrays) {
            if (array == null || array.isEmpty() || " ".equals(array)) {
                continue;
            }
            builder.append(array.toLowerCase());
        }

        str = builder.toString();

        int left = 0, right = str.length() - 1;
        while (left <= right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
