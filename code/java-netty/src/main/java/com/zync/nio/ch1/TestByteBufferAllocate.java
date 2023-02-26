package com.zync.nio.ch1;

import java.nio.ByteBuffer;

/**
 * ByteBuffer Allocate
 *
 * @author luocong
 * @version v1.0
 * @date 2023/2/26 22:33
 */
public class TestByteBufferAllocate {

    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());

        // class java.nio.HeapByteBuffer    java堆内存，读写效率较低，受到GC的影响
        // class java.nio.DirectByteBuffer  java直接内存，读写效率高（少一次数据拷贝），不会受GC影响，分配效率低，使用不当可能会造成内存泄漏
    }
}
