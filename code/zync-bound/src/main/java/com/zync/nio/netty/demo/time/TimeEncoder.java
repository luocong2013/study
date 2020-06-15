package com.zync.nio.netty.demo.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author luoc
 * @version V1.0
 * @package com.zync.nio.netty.demo.time
 * @description: 编码器
 * @date 2017/12/10 18:37
 */
public class TimeEncoder extends MessageToByteEncoder<UnixTime> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) throws Exception {
        out.writeInt((int) msg.value());
    }
}
