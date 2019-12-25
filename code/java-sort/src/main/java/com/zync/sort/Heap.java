package com.zync.sort;

/**
 * Heap
 *
 * @author luocong
 * @version 1.0.0
 * @description 堆
 *              堆中某个节点的值总是大于等于其子节点的值，并且堆是一颗完全二叉树。
 *              堆可以用数组来表示，这是因为堆是完全二叉树，而完全二叉树很容易就存储在数组中。
 *              位置 k 的节点的父节点位置为 k/2，而它的两个子节点的位置分别为 2k 和 2k+1。
 *              这里不使用数组索引为 0 的位置，是为了更清晰地描述节点的位置关系。
 * @date 2019年12月25日 14:42
 */
public class Heap<T extends Comparable<T>> {

    private T[] heap;

    private int length = 0;

    public Heap(int maxLength) {
        this.heap = (T[]) new Comparable[maxLength + 1];
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public int size() {
        return length;
    }

    private boolean less(int i, int j) {
        return heap[i].compareTo(heap[j]) < 0;
    }

    private void swap(int i, int j) {
        T t = heap[i];
        heap[i] = heap[j];
        heap[j] = t;
    }

    /**
     * 上浮
     * 在堆中，当一个节点比父节点大，那么需要交换这个两个节点。
     * 交换后还可能比它新的父节点大，因此需要不断地进行比较和交换操作，把这种操作称为上浮。
     * @param k
     */
    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            swap(k / 2, k);
            k /= 2;
        }
    }

    /**
     * 下浮
     * 类似地，当一个节点比子节点来得小，也需要不断地向下进行比较和交换操作，
     * 把这种操作称为下沉。一个节点如果有两个子节点，应当与两个子节点中最大那个节点进行交换。
     * @param k
     */
    private void sink(int k) {
        while (2 * k <= length) {
            int j = 2 * k;
            if (j < length && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    /**
     * 插入元素
     * 将新元素放到数组末尾，然后上浮到合适的位置。
     * @param v
     */
    public void insert(T v) {
        heap[++length] = v;
        swim(length);
    }

    /**
     * 删除最大的元素
     * 从数组顶端删除最大的元素，并将数组的最后一个元素放到顶端，并让这个元素下沉到合适的位置。
     * @return
     */
    public T delMax() {
        T max = heap[1];
        swap(1, length--);
        heap[length + 1] = null;
        sink(1);
        return max;
    }
}
