package com.ysyue.im.codec;

import com.ysyue.im.protocol.ImMessage;
import com.ysyue.im.util.JacksonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * IM 协议编码器
 * 协议格式：魔数 (4 字节) + 版本号 (1 字节) + 消息类型 (1 字节) + 数据长度 (4 字节) + 数据内容
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/23 9:46
 **/
@Slf4j
public class ImMessageEncoder extends MessageToByteEncoder<ImMessage> {

    private static final int MAGIC_NUMBER = 0x12345678;
    private static final byte VERSION = 1;

    @Override
    protected void encode(ChannelHandlerContext ctx, ImMessage msg, ByteBuf out) throws Exception {
        byte[] data = JacksonUtil.toBytes(msg);

        out.writeInt(MAGIC_NUMBER);
        out.writeByte(VERSION);
        out.writeByte(msg.getType().getCode());
        out.writeInt(data.length);
        out.writeBytes(data);

        log.debug("Message encoded: {}", msg);
    }
}
