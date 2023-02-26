package com.zync.nio.ch1;

import java.nio.ByteBuffer;

import static com.zync.nio.utils.ByteBufferUtil.debugAll;

/**
 * 黏包半包
 * @author luocong
 * @version v1.0
 * @date 2023/2/26 23:12
 */
public class TestByteBufferExam {

    public static void main(String[] args) {
        /**
         * 网络上有多条数据发送给服务端，数据之间使用 \n 进行分割
         * 但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有3条为
         * Hello,world\n
         * I'm zhangsan\n
         * How are you?\n
         *
         * 变成了下面的两个ByteBuffer（黏包，半包）
         * Hello,world\nI'm zhangsan\nHo
         * w are you?\n
         *
         * 现在要求你编写程序，将错乱的数据恢复成原始的按 \n 分割的数据
         */

        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                // 完整消息存入新的ByteBuffer
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从 source 读，向 target 写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }

        source.compact();
    }
}
