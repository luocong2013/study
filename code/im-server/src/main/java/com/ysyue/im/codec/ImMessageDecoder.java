package com.ysyue.im.codec;

import com.ysyue.im.protocol.ImMessage;
import com.ysyue.im.protocol.MessageType;
import com.ysyue.im.util.JacksonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * IM 协议解码器
 * 协议格式：魔数 (4 字节) + 版本号 (1 字节) + 消息类型 (1 字节) + 数据长度 (4 字节) + 数据内容
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/23 9:46
 **/
@Slf4j
public class ImMessageDecoder extends ByteToMessageDecoder {

    private static final int HEADER_LENGTH = 10;
    private static final int MAGIC_NUMBER = 0x12345678;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < HEADER_LENGTH) {
            return;
        }

        in.markReaderIndex();

        int magic = in.readInt();
        if (magic != MAGIC_NUMBER) {
            log.error("Invalid magic number: {}", magic);
            ctx.close(); // 非法连接，直接关闭
            return;
        }

        byte version = in.readByte();
        byte messageType = in.readByte();
        int dataLength = in.readInt();

        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex(); // 数据包不完整，等待下一次读取
            return;
        }

        byte[] data = new byte[dataLength];
        in.readBytes(data);

        ImMessage message = JacksonUtil.toBean(data, ImMessage.class);
        message.setType(MessageType.valueOf(messageType));

        out.add(message);
        log.debug("Message decoded: {}", message);
    }
}
