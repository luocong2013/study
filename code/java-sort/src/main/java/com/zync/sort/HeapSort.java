package com.zync.sort;

/**
 * HeapSort
 *
 * @author luocong
 * @version 1.0.0
 * @description 堆排序
 *              把最大元素和当前堆中数组的最后一个元素交换位置，并且不删除它，
 *              那么就可以得到一个从尾到头的递减序列，从正向来看就是一个递增序列，这就是堆排序。
 * @date 2019年12月25日 15:36
 */
public class HeapSort<T extends Comparable<T>> extends AbstractSort<T> {

    @Override
    public void sort(T[] nums) {
        int length = nums.length - 1;
        for (int k = length / 2; k >= 1; k--) {
            sink(nums, k, length);
        }

        while (length > 1) {
            swap(nums, 1, length--);
            sink(nums, 1, length);
        }
    }

    private void sink(T[] nums, int k, int length) {
        while (2 * k <= length) {
            int j = 2 * k;
            if (j < length && less(nums[j], nums[j + 1])) {
                j++;
            }
            if (!less(nums[k], nums[j])) {
                break;
            }
            swap(nums, k, j);
            k = j;
        }
    }
}
