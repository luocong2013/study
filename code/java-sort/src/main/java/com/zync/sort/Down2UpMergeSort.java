package com.zync.sort;

/**
 * Down2UpMergeSort
 *
 * @author luocong
 * @version 1.0.0
 * @description 自底向上归并排序
 *              先归并那些微型数组，然后成对归并得到的微型数组。
 * @date 2019年12月25日 14:37
 */
public class Down2UpMergeSort<T extends Comparable<T>> extends MergeSort<T> {

    @Override
    public void sort(T[] nums) {
        int length = nums.length;
        aux = (T[]) new Comparable[length];

        for (int sz = 1; sz < length; sz += sz) {
            for (int lo = 0; lo < length - sz; lo += sz + sz) {
                merge(nums, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, length - 1));
            }
        }
    }
}
