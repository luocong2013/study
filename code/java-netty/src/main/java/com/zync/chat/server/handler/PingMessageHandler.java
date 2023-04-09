package com.zync.chat.server.handler;

import com.zync.chat.message.PingMessage;
import com.zync.chat.message.PongMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ping请求 handler
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 21:36
 */
@ChannelHandler.Sharable
public class PingMessageHandler extends SimpleChannelInboundHandler<PingMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PingMessage msg) throws Exception {
        ctx.writeAndFlush(new PongMessage());
    }
}
