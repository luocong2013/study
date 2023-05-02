package com.zync.chat.protocol;

import com.zync.chat.common.utils.Config;
import com.zync.chat.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 可以被共享的 MessageCodec
 *
 * 注意：没有保存状态才可加 @Sharable
 *
 * 必须和 LengthFieldBasedFrameDecoder 一起使用，确保接到的 ByteBuf 消息是完整的
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 21:59
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outs) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        // 1. 4 字节的魔数
        out.writeBytes(new byte[] {1, 2, 3, 4});
        // 2. 1 字节的版本
        out.writeByte(1);
        Algorithm algorithm = Config.getSerializerAlgorithm();
        // 3. 1 字节的序列化方式 jdk 0, json 1
        out.writeByte(algorithm.getSerializerAlgorithm());
        // 4. 1 字节的指令类型
        out.writeByte(msg.getMessageType());
        // 5. 8 个字节
        out.writeLong(msg.getSequenceId());
        // 无意义，对齐填充
        out.writeByte(0xff);
        // 6. 获取内容的字节数组
        byte[] bytes = algorithm.serialize(msg);
        // 7. 长度
        out.writeInt(bytes.length);
        // 8. 写入内容
        out.writeBytes(bytes);
        outs.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializerAlgorithm = in.readByte();
        byte messageType = in.readByte();
        long sequenceId = in.readLong();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);

        // 找到序列化算法
        Algorithm algorithm = Algorithm.getAlgorithm(serializerAlgorithm);
        Class<? extends Message> clazz = Message.getMessageClass(messageType);
        Message message = algorithm.deserialize(clazz, bytes);
        log.debug("{}, {}, {}, {}, {}, {}", magicNum, version, serializerAlgorithm, messageType, sequenceId, length);
        log.debug("message: {}", message);
        out.add(message);
    }
}
