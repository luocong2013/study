package com.ysyue.im.processor;

import com.ysyue.im.protocol.ImMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 处理登出消息
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/9 13:20
 **/
@Slf4j
@Component
public class LogoutImMessageProcessor extends AbstractImMessageProcessor {

    @Override
    public void processor(ChannelHandlerContext ctx, ImMessage message) {
        sessionManager.unbind(ctx.channel());
        ctx.close();
        log.info("User logged out: {}", message.getSenderId());
    }
}
