package com.ysyue.im.processor;

import com.ysyue.im.protocol.ImMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 处理确认消息
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/9 13:21
 **/
@Slf4j
@Component
public class AckImMessageProcessor extends AbstractImMessageProcessor {

    @Override
    public void processor(ChannelHandlerContext ctx, ImMessage message) {
        log.debug("Message acknowledged: {}", message.getMessageId());
    }
}
