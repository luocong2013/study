package com.ysyue.im.processor;

import com.ysyue.im.protocol.ImMessage;
import com.ysyue.im.protocol.MessageType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 处理单聊消息
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/9 13:14
 **/
@Slf4j
@Component
public class ChatImMessageProcessor extends AbstractImMessageProcessor {

    @Override
    public void processor(ChannelHandlerContext ctx, ImMessage message) {
        String receiverId = message.getReceiverId();

        // 异步保存消息到数据库 (不阻塞Netty线程)
        chatMessageService.saveMessageAsync(message);

        Channel receiverChannel = sessionManager.getChannel(receiverId);
        if (receiverChannel != null && receiverChannel.isActive()) {
            writeAndFlush(receiverChannel, message);
        } else {
            // TODO: 处理离线消息（可以使用Redis存储未读红点，极光推送等）
            log.info("用户 {} 离线，消息转为离线存储", message.getReceiverId());
        }

        ImMessage ack = ImMessage.builder()
                .type(MessageType.ACK)
                .messageId(message.getMessageId())
                .timestamp(System.currentTimeMillis())
                .ext("消息已送达")
                .build();

        writeAndFlush(ctx, ack);
        log.info("Message from {} to {} delivered", message.getSenderId(), receiverId);
    }

}
