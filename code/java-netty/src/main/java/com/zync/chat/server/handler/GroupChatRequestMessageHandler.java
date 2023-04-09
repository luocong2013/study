package com.zync.chat.server.handler;

import com.zync.chat.message.GroupChatRequestMessage;
import com.zync.chat.message.GroupChatResponseMessage;
import com.zync.chat.server.session.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * 群聊消息 handler
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/8 17:00
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        List<Channel> channels = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());
        GroupChatResponseMessage message = new GroupChatResponseMessage(msg.getFrom(), msg.getContent());
        channels.forEach(channel -> channel.writeAndFlush(message));
    }
}
