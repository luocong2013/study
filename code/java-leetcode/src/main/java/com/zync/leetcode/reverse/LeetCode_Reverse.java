package com.zync.leetcode.reverse;

/**
 * @user luoc
 * @date 2020/2/16 15:43
 * @description LeetCode 递归练习
 **/
public class LeetCode_Reverse {

    public static void main(String[] args) {
        char[] s = {'H', 'E', 'L', 'L', 'O'};
        // helper(0, s);
        reverseString(s);
        for (char c : s) {
            System.out.println(c);
        }
    }

    public static void helper(int index, char[] s) {
        if (s == null || index >= s.length) {
            return;
        }
        helper(index + 1, s);
        System.out.println(s[index]);
    }

    public static void reverseString(char[] s) {
        int i = 0;
        int j = s.length - 1;
        while (i < j) {
            swap(s, i, j);
            i++;
            j--;
        }
    }

    public static void swap(char[] s, int i, int j) {
        char temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
}
