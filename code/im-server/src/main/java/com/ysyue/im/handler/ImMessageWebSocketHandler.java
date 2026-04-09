package com.ysyue.im.handler;

import com.ysyue.im.protocol.ImMessage;
import com.ysyue.im.session.SessionManager;
import com.ysyue.im.util.JacksonUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * web socket 处理器
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/8 16:38
 **/
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class ImMessageWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final SessionManager sessionManager;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        log.info("Channel registered: {}", ctx.channel().id());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        log.info("Received message: {}", text);
        ImMessage message = JacksonUtil.toBean(text, ImMessage.class);
        message.getType().getProcessor().processor(ctx, message);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {
            if (event.state() == IdleState.READER_IDLE) {
                log.warn("Read idle timeout, closing connection: {}", ctx.channel().id());
                sessionManager.unbind(ctx.channel());
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception caught: {}", cause.getMessage(), cause);
        sessionManager.unbind(ctx.channel());
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel inactive: {}", ctx.channel().id());
        sessionManager.unbind(ctx.channel());
    }

}
