package com.zync.nio.ch1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.zync.nio.utils.ByteBufferUtil.debugAll;

/**
 * 字符串 <==> ByteBuffer 转换
 *
 * @author luocong
 * @version v1.0
 * @date 2023/2/26 22:50
 */
public class TestByteBufferString {

    public static void main(String[] args) {
        // 1. 字符串转为ByteBuffer
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        debugAll(buffer1);

        // 2. Charset 自动切换至模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer2);

        // 3. wrap 自动切换至模式
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        debugAll(buffer3);

        // 4. ByteBuffer转为字符串
        String s1 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(s1);

        buffer1.flip();
        String s2 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(s2);
    }
}
