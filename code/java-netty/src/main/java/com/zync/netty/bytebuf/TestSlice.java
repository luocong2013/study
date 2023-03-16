package com.zync.netty.bytebuf;

import com.zync.netty.bytebuf.utils.ByteBufLogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * ByteBuf slice
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/16 22:22
 */
@Slf4j
public class TestSlice {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
        buffer.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
        ByteBufLogUtil.log(buffer);

        // 在切片的过程中，没有发生数据复制
        ByteBuf f1 = buffer.slice(0, 5);
        ByteBuf f2 = buffer.slice(5, 5);

        // 需要让切片的 ByteBuf 引用计算加一
        f1.retain();
        f2.retain();

        // 释放原有 ByteBuf 内存
        buffer.release();

        ByteBufLogUtil.log(f1);
        ByteBufLogUtil.log(f2);

        // 更改原始 ByteBuf 中的值
        log.debug("=================修改原ByteBuf中的值======================");
        buffer.setByte(0, 'h');

        log.debug("=================打印f1======================");
        System.out.println(ByteBufUtil.prettyHexDump(f1));

        f1.release();
        f2.release();
    }
}
