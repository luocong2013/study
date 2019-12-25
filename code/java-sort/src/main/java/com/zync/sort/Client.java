package com.zync.sort;

import java.util.Arrays;

/**
 * Client
 *
 * @author luocong
 * @version 1.0.0
 * @description 排序测试
 * @date 2019年12月25日 13:56
 */
public class Client {

    public static void main(String[] args){
        Integer[] nums = {1, 7, 2, 5, 9};

        // 选择排序
        //AbstractSort<Integer> selection = new Selection<>();
        //selection.sort(nums);

        // 冒泡排序
        //AbstractSort<Integer> bubble = new Bubble<>();
        //bubble.sort(nums);

        // 插入排序
        //AbstractSort<Integer> insertion = new Insertion<>();
        //insertion.sort(nums);

        // 希尔排序
        //AbstractSort<Integer> shell = new Shell<>();
        //shell.sort(nums);

        // 快速排序
        //AbstractSort<Integer> quickSort = new QuickSort<>();
        //quickSort.sort(nums);

        // 三向切分快速排序
        //AbstractSort<Integer> threeWayQuickSort = new ThreeWayQuickSort<>();
        //threeWayQuickSort.sort(nums);

        // 自底向上归并排序
        //AbstractSort<Integer> down2UpMergeSort = new Down2UpMergeSort<>();
        //down2UpMergeSort.sort(nums);

        // 自顶向下归并排序
        //AbstractSort<Integer> up2DownMergeSort = new Up2DownMergeSort<>();
        //up2DownMergeSort.sort(nums);

        // 堆排序
        AbstractSort<Integer> heapSort = new HeapSort<>();
        heapSort.sort(nums);

        Arrays.stream(nums).forEach(System.out::println);
    }
}
