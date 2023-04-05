package com.zync.chat.protocol;

import com.zync.chat.message.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 测试 MessageCodec
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 20:57
 */
public class MessageCodecTester {

    public static void main(String[] args) throws Exception {
        // 1、测试 decode 方法
        decodeTester();

        // 2、测试 encode 方法
        //encodeTester();
    }

    /**
     * 测试 decode 方法
     * @throws Exception
     */
    static void decodeTester() throws Exception {
        // LoggingHandler 加了 @Sharable 表示可以被多线程共享 可以只创建一个
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                loggingHandler,
                new MessageCodec()
        );

        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123", "张三");
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buffer);

        ByteBuf s1 = buffer.slice(0, 100);
        ByteBuf s2 = buffer.slice(100, buffer.readableBytes() - 100);
        s1.retain();
        channel.writeInbound(s1);
        channel.writeInbound(s2);
    }

    /**
     * 测试 encode 方法
     */
    static void encodeTester() {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG),
                new MessageCodec()
        );

        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123", "张三");
        channel.writeOutbound(message);
    }
}
