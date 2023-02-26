package com.zync.nio.ch1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static com.zync.nio.utils.ByteBufferUtil.debugAll;

/**
 * 分散读取
 *
 * @author luocong
 * @version v1.0
 * @date 2023/2/26 23:03
 */
public class TestScatteringReads {

    public static void main(String[] args) {
        try (RandomAccessFile accessFile = new RandomAccessFile("words.txt", "r")) {
            FileChannel channel = accessFile.getChannel();
            ByteBuffer b1 = ByteBuffer.allocate(3);
            ByteBuffer b2 = ByteBuffer.allocate(3);
            ByteBuffer b3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{b1, b2, b3});
            debugAll(b1);
            debugAll(b2);
            debugAll(b3);
        } catch (IOException ignore) {
        }
    }
}
