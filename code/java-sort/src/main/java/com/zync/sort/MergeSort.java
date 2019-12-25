package com.zync.sort;

/**
 * MergeSort
 *
 * @author luocong
 * @version 1.0.0
 * @description 归并排序
 *              归并排序的思想是将数组分成两部分，分别进行排序，然后归并起来。
 * @date 2019年12月25日 14:31
 */
public abstract class MergeSort<T extends Comparable<T>> extends AbstractSort<T> {

    protected T[] aux;

    /**
     * 归并方法
     * 将数组中两个已经排序的部分归并成一个
     * @param nums
     * @param l
     * @param m
     * @param h
     */
    protected void merge(T[] nums, int l, int m, int h) {
        int i = l, j = m + 1;

        // 将数据复制到辅助数组
        for (int k = l; k <= h; k++) {
            aux[k] = nums[k];
        }

        for (int k = l; k <= h; k++) {
            if (i > m) {
                nums[k] = aux[j++];
            } else if (j > h) {
                nums[k] = aux[i++];
            } else if (aux[i].compareTo(aux[j]) <= 0) {
                // 先进行这一步，保证稳定性
                nums[k] = aux[i++];
            } else {
                nums[k] = aux[j++];
            }
        }
    }

}
