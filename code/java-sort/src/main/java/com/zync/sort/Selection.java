package com.zync.sort;

/**
 * Selection
 *
 * @author luocong
 * @version 1.0.0
 * @description 选择排序
 *              从数组中选择最小元素，将它与数组的第一个元素交换位置。再从数组剩下的元素中选择出最小的元素，
 *              将它与数组的第二个元素交换位置。不断进行这样的操作，直到将整个数组排序。
 *              选择排序需要 ~N2/2 次比较和 ~N 次交换，它的运行时间与输入无关，这个特点使得它对一个已经排序的数组也需要这么多的比较和交换操作。
 * @date 2019年12月25日 13:49
 */
public class Selection<T extends Comparable<T>> extends AbstractSort<T> {

    @Override
    public void sort(T[] nums) {
        int length = nums.length;
        for (int i = 0; i < length - 1; i++) {
            int min = i;
            for (int k = i + 1; k < length; k++) {
                if (less(nums[k], nums[min])) {
                    min = k;
                }
            }
            swap(nums, i, min);
        }
    }
}
