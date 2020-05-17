package com.zync.leetcode.huawei;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * @user luoc
 * @date 2020/5/17 14:36
 * @description 两个字符串数字求和
 **/
public class SumTwoStringNumber {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String num1 = input.nextLine();
        String num2 = input.nextLine();
        System.out.println(sum(num1, num2));
    }

    private static String sum(String num1, String num2) {
        String minStr = num1;
        String maxStr = num2;
        if (num1.length() > num2.length()) {
            minStr = num2;
            maxStr = num1;
        }
        int length = maxStr.length();
        minStr = StringUtils.leftPad(minStr, length, '0');

        StringBuilder builder = new StringBuilder();
        int mod = 0;
        for (int i = length - 1; i >= 0; i--) {

            int a = Integer.parseInt(minStr.charAt(i)+"");
            int b = Integer.parseInt(maxStr.charAt(i)+"");
            int sum = a + b + mod;
            if (sum >= 10) {
                mod = 1;
            } else {
                mod = 0;
            }
            builder.append(sum % 10);
        }
        return builder.reverse().toString();
    }
}
