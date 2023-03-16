package com.zync.netty.bytebuf;

import com.zync.netty.bytebuf.utils.ByteBufLogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * ByteBuf读
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/16 21:49
 */
@Slf4j
public class TestByteBufRead {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(16, 160);
        // 向 ByteBuf 中写入数据
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        buffer.writeInt(5);
        ByteBufLogUtil.log(buffer);

        // 读取4个字节
        log.debug("{}", buffer.readByte());
        log.debug("{}", buffer.readByte());
        log.debug("{}", buffer.readByte());
        log.debug("{}", buffer.readByte());
        ByteBufLogUtil.log(buffer);

        // 通过mark与reset实现重复读取
        buffer.markReaderIndex();
        log.debug("{}", buffer.readInt());
        ByteBufLogUtil.log(buffer);

        // 恢复到mark标记处
        buffer.resetReaderIndex();
        ByteBufLogUtil.log(buffer);
    }
}
