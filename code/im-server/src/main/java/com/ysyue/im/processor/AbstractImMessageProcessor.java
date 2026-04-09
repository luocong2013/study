package com.ysyue.im.processor;

import com.ysyue.im.protocol.ImMessage;
import com.ysyue.im.protocol.MessageType;
import com.ysyue.im.service.ChatMessageService;
import com.ysyue.im.session.SessionManager;
import com.ysyue.im.util.IdUtil;
import com.ysyue.im.util.JacksonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;

/**
 * IM 消息处理抽象类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/9 13:05
 **/
public abstract class AbstractImMessageProcessor implements ImMessageProcessor {

    @Resource
    protected SessionManager sessionManager;
    @Resource
    protected ChatMessageService chatMessageService;

    /**
     * 发送消息
     *
     * @param ctx     通道
     * @param message 消息
     */
    protected void writeAndFlush(ChannelHandlerContext ctx, ImMessage message) {
        ctx.writeAndFlush(new TextWebSocketFrame(JacksonUtil.toJson(message)));
    }

    /**
     * 发送错误响应
     */
    protected void sendErrorResponse(ChannelHandlerContext ctx, String errorMsg) {
        ImMessage error = ImMessage.builder()
                .type(MessageType.ERROR)
                .messageId(IdUtil.getIdStr())
                .timestamp(System.currentTimeMillis())
                .content(errorMsg)
                .build();

        writeAndFlush(ctx, error);
    }

}
