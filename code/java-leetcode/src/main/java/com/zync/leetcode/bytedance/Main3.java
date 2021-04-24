package com.zync.leetcode.bytedance;

import java.util.Scanner;

/**
 * 找零
 **/
public class Main3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int money = scanner.nextInt();
        int div = 1024 - money;
        int[] arrays = {64, 16, 4, 1};
        int count = 0;
        for (int array : arrays) {
            count += div / array;
            div = div % array;
        }
        System.out.println(count);
    }
}
