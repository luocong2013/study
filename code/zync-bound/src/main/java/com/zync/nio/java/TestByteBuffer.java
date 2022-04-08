package com.zync.nio.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * java 自带 Channel 使用
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/6 17:06
 */
public class TestByteBuffer {

    public static void main(String[] args) {
        // FileChannel
        // 1. 输入输出流  2. RandomAccessFile
        try (FileChannel channel = new FileInputStream("/Users/luocong/Downloads/data.txt").getChannel()) {
            // 准备缓冲区 10个字节
            ByteBuffer buffer = ByteBuffer.allocate(10);
            int length;
            while (true) {
                length = channel.read(buffer);
                System.out.println("读取到的字节数: " + length);
                if (length == -1) {
                    break;
                }
                // 打印buffer内容
                // 将buffer切换至读模式
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.println("实际字节: " + (char) b);
                }
                // 读完一次，清空buffer，将buffer切换至写模式
                // 或使用 compact() compact 不会清空 buffer，但是会清理掉已经读取的字节，保留未读取的字节
                buffer.clear();
            }
        } catch (IOException ignore) {
        }
    }
}
