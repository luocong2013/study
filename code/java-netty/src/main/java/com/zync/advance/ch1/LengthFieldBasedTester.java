package com.zync.advance.ch1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

/**
 * LTC解码器
 * io.netty.handler.codec.LengthFieldBasedFrameDecoder
 * lengthFieldOffset   - 长度字段偏移量
 * lengthFieldLength   - 长度字段长度
 * lengthAdjustment    - 长度字段为基准，还有几个字节是内容
 * initialBytesToStrip - 从头剥离几个字节
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/3 21:28
 */
public class LengthFieldBasedTester {

    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 1, 5),
                new LoggingHandler(LogLevel.DEBUG)
        );

        // 4个字节的内容长度，实际内容
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        sender(buffer, "Hello, world");
        sender(buffer, "Hi!");

        channel.writeInbound(buffer);
    }

    private static void sender(ByteBuf buffer, String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        // 内容长度
        buffer.writeInt(length);
        // 版本号
        buffer.writeByte(1);
        // 内容
        buffer.writeBytes(bytes);
    }
}
