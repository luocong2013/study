package com.zync.nio.ch1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * FileChannel transferTo
 * @author luocong
 * @version v1.0
 * @date 2023/3/5 22:21
 */
public class TestFileChannelTransferTo {

    public static void main(String[] args) {
        try (FileInputStream input = new FileInputStream("data.txt");
             FileOutputStream out = new FileOutputStream("to.txt")) {
            FileChannel from = input.getChannel();
            FileChannel to = out.getChannel();

            // 效率高，底层会利用操作系统的零拷贝进行优化，拷贝 2g 数据
            long size = from.size();
            for (long left = size; left > 0; ) {
                System.out.println("position: " + (size - left) + " left: " + left);
                left -= from.transferTo(size - left, left, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
