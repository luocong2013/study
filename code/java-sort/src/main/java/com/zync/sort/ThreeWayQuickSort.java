package com.zync.sort;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 三向切分快速排序
 *             对于有大量重复元素的数组，可以将数组切分为三部分，分别对应小于、等于和大于切分元素。
 *             三向切分快速排序对于有大量重复元素的随机数组可以在线性时间内完成排序。
 * @date 2019/12/25 21:32
 */
public class ThreeWayQuickSort<T extends Comparable<T>> extends QuickSort<T> {

    @Override
    protected void sort(T[] nums, int l, int h) {
        if (h <= l) {
            return;
        }
        int lt = l, i = l + 1, gt = h;
        T v = nums[l];
        while (i <= gt) {
            int cmp = nums[i].compareTo(v);
            if (cmp < 0) {
                swap(nums, lt++, i++);
            } else if (cmp > 0) {
                swap(nums, i, gt--);
            } else {
                i++;
            }
        }
        sort(nums, l, lt - 1);
        sort(nums, gt + 1, h);
    }
}
