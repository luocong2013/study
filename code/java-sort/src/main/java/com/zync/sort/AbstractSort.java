package com.zync.sort;

/**
 * AbstractSort
 *
 * @author luocong
 * @version 1.0.0
 * @description 排序基类
 * @date 2019年12月25日 13:46
 */
public abstract class AbstractSort<T extends Comparable<T>> {

    /**
     * 排序
     * @param nums 待排序数组
     */
    public abstract void sort(T[] nums);

    /**
     * 比较
     * @param v
     * @param w
     * @return
     */
    protected boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    /**
     * 交换
     * @param nums 待排序数组
     * @param i
     * @param k
     */
    protected void swap(T[] nums, int i, int k) {
        T t = nums[i];
        nums[i] = nums[k];
        nums[k] = t;
    }

}
