package com.zync.sort;

/**
 * Bubble
 *
 * @author luocong
 * @version 1.0.0
 * @description 冒泡排序
 *              从左到右不断交换相邻逆序的元素，在一轮的循环之后，可以让未排序的最大元素上浮到右侧。
 *              在一轮循环中，如果没有发生交换，那么说明数组已经是有序的，此时可以直接退出。
 * @date 2019年12月25日 13:52
 */
public class Bubble<T extends Comparable<T>> extends AbstractSort<T> {

    @Override
    public void sort(T[] nums) {
        int length = nums.length;
        boolean isSorted = false;
        for (int i = length - 1; i > 0 && !isSorted; i--) {
            isSorted = true;
            for (int k = 0; k < i; k++) {
                if (less(nums[k + 1], nums[k])) {
                    isSorted = false;
                    swap(nums, k, k + 1);
                }
            }
        }
    }
}
