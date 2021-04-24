package com.zync.leetcode.bytedance;

import java.util.Arrays;

/**
 * @user luoc
 * @date 2021/4/23 21:01
 * 1,2,3,9,8,4,2,1 返回刚好大于这个数的下一个数
 * @description
 **/
public class Interview {

    public static void main(String[] args) {
//        int[] array = {1, 2, 3, 4, 2, 1};  // 1 2 4 1 2 3
        int[] array = {1, 2, 3, 9, 8, 4, 2, 1};
        System.out.println(Arrays.toString(temp(array)));
    }

    private static int[] temp(int[] array) {
        int length = array.length;
        int k = -1;
        for (int i = length - 1; i >= 1; i--) {
            if (array[i - 1] < array[i]) {
                k = i - 1;
                break;
            }
        }

        if (k == -1) {
            return array;
        }
        int[] temp = new int[length - k - 1];
        System.arraycopy(array, k + 1, temp, 0, length - k - 1);
        Arrays.sort(temp);

        for (int i = 0; i < length - k - 1; i++) {
            if (temp[i] > array[k]) {
                int t = temp[i];
                temp[i] = array[k];
                array[k] = t;
                break;
            }
        }

        System.arraycopy(temp, 0, array, k + 1, length - k - 1);
        return array;
    }
}
