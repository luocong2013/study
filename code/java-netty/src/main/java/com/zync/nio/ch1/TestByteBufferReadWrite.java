package com.zync.nio.ch1;

import java.nio.ByteBuffer;

import static com.zync.nio.utils.ByteBufferUtil.debugAll;

/**
 * ByteBuffer读写
 *
 * @author luocong
 * @version v1.0
 * @date 2023/2/26 22:20
 */
public class TestByteBufferReadWrite {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 写入数据
        buffer.put((byte) 0x61);
        debugAll(buffer);
        // 写入byte数组
        buffer.put(new byte[]{0x62, 0x63, 0x64});
        debugAll(buffer);

        // 切换至读模式
        buffer.flip();
        System.out.println((char) buffer.get());
        debugAll(buffer);

        // 清理掉已经读取的字节，保留未读取的字节，将buffer切换至写模式
        buffer.compact();
        debugAll(buffer);

        buffer.put(new byte[]{0x65, 0x66});
        debugAll(buffer);
    }
}
