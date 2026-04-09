package com.ysyue.im.processor;

import com.ysyue.im.protocol.ImMessage;
import com.ysyue.im.protocol.MessageType;
import com.ysyue.im.util.IdUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 处理心跳消息
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/9 13:18
 **/
@Slf4j
@Component
public class HeartbeatImMessageProcessor extends AbstractImMessageProcessor {

    @Override
    public void processor(ChannelHandlerContext ctx, ImMessage message) {
        ImMessage response = ImMessage.builder()
                .type(MessageType.HEARTBEAT_RESPONSE)
                .messageId(IdUtil.getIdStr())
                .timestamp(System.currentTimeMillis())
                .build();

        writeAndFlush(ctx, response);
        log.debug("Heartbeat received from {}", ctx.channel().id());
    }
}
