package com.ysyue.im.handler;

import com.ysyue.im.protocol.ImMessage;
import com.ysyue.im.protocol.MessageType;
import com.ysyue.im.session.SessionManager;
import com.ysyue.im.util.IdUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * IM 消息业务处理器
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/23 9:48
 **/
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class ImMessageHandler extends SimpleChannelInboundHandler<ImMessage> {

    private final SessionManager sessionManager;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ImMessage msg) throws Exception {
        log.info("Received message: {}", msg);

        switch (msg.getType()) {
            case LOGIN:
                handleLogin(ctx, msg);
                break;
            case CHAT:
                handleChat(ctx, msg);
                break;
            case GROUP_CHAT:
                handleGroupChat(ctx, msg);
                break;
            case HEARTBEAT:
                handleHeartbeat(ctx);
                break;
            case LOGOUT:
                handleLogout(ctx, msg);
                break;
            case ACK:
                handleAck(ctx, msg);
                break;
            default:
                log.warn("Unknown message type: {}", msg.getType());
        }
    }

    /**
     * 处理登录请求
     */
    private void handleLogin(ChannelHandlerContext ctx, ImMessage msg) {
        String userId = msg.getSenderId();

        if (StringUtils.isBlank(userId)) {
            sendErrorResponse(ctx, "用户 ID 不能为空");
            return;
        }

        sessionManager.bind(userId, ctx.channel());

        ImMessage response = ImMessage.builder()
                .type(MessageType.LOGIN_RESPONSE)
                .messageId(IdUtil.getIdStr())
                .timestamp(System.currentTimeMillis())
                .ext("登录成功")
                .build();

        ctx.writeAndFlush(response);
        log.info("User {} logged in successfully", userId);
    }

    /**
     * 处理单聊消息
     */
    private void handleChat(ChannelHandlerContext ctx, ImMessage msg) {
        String receiverId = msg.getReceiverId();

        Channel receiverChannel = sessionManager.getChannel(receiverId);
        if (receiverChannel == null || !receiverChannel.isActive()) {
            sendErrorResponse(ctx, "对方不在线");
            return;
        }

        receiverChannel.writeAndFlush(msg);

        ImMessage ack = ImMessage.builder()
                .type(MessageType.ACK)
                .messageId(msg.getMessageId())
                .timestamp(System.currentTimeMillis())
                .ext("消息已送达")
                .build();

        ctx.writeAndFlush(ack);
        log.info("Message from {} to {} delivered", msg.getSenderId(), receiverId);
    }

    /**
     * 处理群聊消息（简化版，实际需要使用 Group 管理）
     */
    private void handleGroupChat(ChannelHandlerContext ctx, ImMessage msg) {
        String groupId = msg.getReceiverId();
        log.info("Group message to {}: {}", groupId, msg.getContent());

        ImMessage ack = ImMessage.builder()
                .type(MessageType.ACK)
                .messageId(msg.getMessageId())
                .timestamp(System.currentTimeMillis())
                .ext("群消息已发送")
                .build();

        ctx.writeAndFlush(ack);
    }

    /**
     * 处理心跳
     */
    private void handleHeartbeat(ChannelHandlerContext ctx) {
        ImMessage response = ImMessage.builder()
                .type(MessageType.HEARTBEAT_RESPONSE)
                .messageId(IdUtil.getIdStr())
                .timestamp(System.currentTimeMillis())
                .build();

        ctx.writeAndFlush(response);
        log.debug("Heartbeat received from {}", ctx.channel().id());
    }

    /**
     * 处理登出
     */
    private void handleLogout(ChannelHandlerContext ctx, ImMessage msg) {
        sessionManager.unbind(ctx.channel());
        ctx.close();
        log.info("User logged out: {}", msg.getSenderId());
    }

    /**
     * 处理消息确认
     */
    private void handleAck(ChannelHandlerContext ctx, ImMessage msg) {
        log.debug("Message acknowledged: {}", msg.getMessageId());
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

    /**
     * 发送错误响应
     */
    private void sendErrorResponse(ChannelHandlerContext ctx, String errorMsg) {
        ImMessage error = ImMessage.builder()
                .type(MessageType.ERROR)
                .messageId(IdUtil.getIdStr())
                .timestamp(System.currentTimeMillis())
                .content(errorMsg)
                .build();

        ctx.writeAndFlush(error);
    }
}
