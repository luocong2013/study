package com.zync.nio.ch1;

import java.nio.ByteBuffer;

import static com.zync.nio.utils.ByteBufferUtil.debugAll;

/**
 * ByteBuffer Read
 *
 * @author luocong
 * @version v1.0
 * @date 2023/2/26 22:41
 */
public class TestByteBufferRead {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();
        debugAll(buffer);

        buffer.get(new byte[4]);
        debugAll(buffer);

        // rewind 从头开始读
        buffer.rewind();
        debugAll(buffer);
        System.out.println((char) buffer.get());

        // mark & reset
        // mark 做一个标识，记录 position 位置，reset 是将 position 重置到 mark 的位置
        System.out.println((char) buffer.get());
        // 加标记，索引为 2 的位置
        buffer.mark();
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        // 将 position 重置到索引2
        buffer.reset();
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());


        // get(i) 不会改变读索引的位置
        System.out.println((char) buffer.get(3));
        debugAll(buffer);
    }
}
