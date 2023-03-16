package com.zync.netty.bytebuf;

import com.zync.netty.bytebuf.utils.ByteBufLogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * ByteBuf写入
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/16 21:43
 */
public class TestWriteByteBuf {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(16, 160);
        ByteBufLogUtil.log(buffer);

        // 向 ByteBuf 中写入数据
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        ByteBufLogUtil.log(buffer);

        buffer.writeInt(5);
        ByteBufLogUtil.log(buffer);

        buffer.writeIntLE(6);
        ByteBufLogUtil.log(buffer);

        buffer.writeLong(7);
        ByteBufLogUtil.log(buffer);
    }
}
