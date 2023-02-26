package com.zync.nio.ch1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 集中写入
 *
 * @author luocong
 * @version v1.0
 * @date 2023/2/26 23:07
 */
public class TestGatheringWrites {

    public static void main(String[] args) {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("word");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("中国人");

        try (RandomAccessFile accessFile = new RandomAccessFile("words2.txt", "rw")) {
            FileChannel channel = accessFile.getChannel();
            channel.write(new ByteBuffer[]{b1, b2, b3});
        } catch (IOException ignore) {
        }
    }
}
