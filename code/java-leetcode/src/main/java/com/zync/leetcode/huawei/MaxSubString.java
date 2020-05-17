package com.zync.leetcode.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @user luoc
 * @date 2020/4/25 21:34
 * @description 最长子串
 **/
public class MaxSubString {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int lineCount = Integer.parseInt(input.nextLine());
        List<InputData> inputDatas = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            String strA = input.nextLine();
            String strB = input.nextLine();
            inputDatas.add(new InputData(strA, strB));
        }
        if (inputDatas.size() > 0) {
            inputDatas.forEach(item -> System.out.println(computer(item.getStrA(), item.getStrB())));
        }
    }

    private static int computer(String strA, String strB) {
        int maxLength = 0;
        int lengthA = strA.length();
        for (int i = 0; i < lengthA; i++) {
            for (int k = i + 1; k <= lengthA; k++) {
                String substr = strA.substring(i, k);
                if (strB.contains(substr)) {
                    int tempLength = k - i;
                    if (tempLength > maxLength) {
                        maxLength = tempLength;
                    }
                }
            }
        }
        return maxLength;
    }

    public static class InputData {
        private String strA;
        private String strB;

        public InputData(String strA, String strB) {
            this.strA = strA;
            this.strB = strB;
        }

        public String getStrA() {
            return strA;
        }

        public void setStrA(String strA) {
            this.strA = strA;
        }

        public String getStrB() {
            return strB;
        }

        public void setStrB(String strB) {
            this.strB = strB;
        }
    }
}
