package com.zync.nio.ch1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * java 自带 Channel 使用
 *
 * @author luocong
 * @version v1.0
 * @date 2023/2/26 21:55
 */
@Slf4j
public class TestByteBuffer {

    public static void main(String[] args) {
        // FileChannel
        // 1. 输入输出流
        // 2. RandomAccessFile
        try (FileInputStream inputStream = new FileInputStream("data.txt")) {
            FileChannel channel = inputStream.getChannel();
            // 准备缓冲区 10个字节
            ByteBuffer buffer = ByteBuffer.allocate(10);
            int length;
            while (true) {
                length = channel.read(buffer);
                log.info("读取到的字节数: {}", length);
                if (length == -1) {
                    break;
                }
                // 打印buffer内容
                // 将buffer切换至读模式
                buffer.flip();
                // 是否还有剩余未读数据
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    log.info("实际字节: {}", (char) b);
                }
                // 读完一次，清空buffer，将buffer切换至写模式
                // 或使用 compact() compact 不会清空 buffer，但是会清理掉已经读取的字节，保留未读取的字节
                buffer.clear();
            }
        } catch (IOException ignore) {
        }
    }
}
