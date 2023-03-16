package com.zync.netty.bytebuf.utils;

import io.netty.buffer.ByteBuf;
import lombok.experimental.UtilityClass;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * ByteBuf工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/3/16 21:17
 */
@UtilityClass
public class ByteBufLogUtil {

    public void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0? 0 : 1) + 4;
        int outputLength = 2 + rows * 80;
        StringBuilder buf = new StringBuilder(outputLength)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf);
    }
}
