package com.zync.sort;

/**
 * Shell
 *
 * @author luocong
 * @version 1.0.0
 * @description 希尔排序
 *              对于大规模的数组，插入排序很慢，因为它只能交换相邻的元素，每次只能将逆序数量减少 1。
 *              希尔排序的出现就是为了解决插入排序的这种局限性，它通过交换不相邻的元素，每次可以将逆序数量减少大于 1。
 *              希尔排序使用插入排序对间隔 h 的序列进行排序。通过不断减小 h，最后令 h=1，就可以使得整个数组是有序的。
 * @date 2019年12月25日 14:28
 */
public class Shell<T extends Comparable<T>> extends AbstractSort<T> {

    @Override
    public void sort(T[] nums) {
        int length = nums.length;
        int h = 1;
        while (h < length / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < length; i++) {
                for (int k = i; k >= h && less(nums[k], nums[k - h]); k -= h) {
                    swap(nums, k, k -h);
                }
            }
            h /= 3;
        }
    }
}
