package com.zync.netty.bytebuf;

import com.zync.netty.bytebuf.utils.ByteBufLogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

/**
 * ByteBuf Composite
 * 组合两个ByteBuf 避免拷贝
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/16 22:38
 */
public class TestCompositeByteBuf {

    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[]{6, 7, 8, 9, 10});

        // 进行了数据的内存复制操作
        //ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        //buffer.writeBytes(buf1).writeBytes(buf2);
        //ByteBufLogUtil.log(buffer);

        // CompositeByteBuf 对外是一个虚拟视图，组合这些 ByteBuf 不会产生内存复制
        CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
        buffer.addComponents(true, buf1, buf2);
        ByteBufLogUtil.log(buffer);
    }
}
