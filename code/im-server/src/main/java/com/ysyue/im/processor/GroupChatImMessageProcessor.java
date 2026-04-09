package com.ysyue.im.processor;

import com.ysyue.im.protocol.ImMessage;
import com.ysyue.im.protocol.MessageType;
import com.ysyue.im.util.JacksonUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 处理群聊消息
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/9 13:16
 **/
@Slf4j
@Component
public class GroupChatImMessageProcessor extends AbstractImMessageProcessor {

    @Override
    public void processor(ChannelHandlerContext ctx, ImMessage message) {
        String groupId = message.getReceiverId();
        log.info("Group message to {}: {}", groupId, message.getContent());

        // 异步保存消息到数据库 (不阻塞Netty线程)
        chatMessageService.saveGroupMessageAsync(message);

        // 2. 获取群内所有成员ID (此处最好加Redis缓存，避免频繁查库)
        List<String> memberIds = chatMessageService.getGroupMemberIds(groupId);

        // 3. 遍历成员发送消息
        String msgJson = JacksonUtil.toJson(message);
        for (String memberId : memberIds) {
            // 不发给自己
            if (StringUtils.equals(memberId, message.getSenderId())) {
                continue;
            }

            Channel channel = sessionManager.getChannel(memberId);
            if (channel != null && channel.isActive()) {
                channel.writeAndFlush(new TextWebSocketFrame(msgJson));
            }
        }

        ImMessage ack = ImMessage.builder()
                .type(MessageType.ACK)
                .messageId(message.getMessageId())
                .timestamp(System.currentTimeMillis())
                .ext("群消息已发送")
                .build();

        writeAndFlush(ctx, ack);
    }

}
