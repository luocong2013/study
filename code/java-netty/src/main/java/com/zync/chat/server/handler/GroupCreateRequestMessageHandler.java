package com.zync.chat.server.handler;

import com.zync.chat.message.GroupCreateRequestMessage;
import com.zync.chat.message.GroupCreateResponseMessage;
import com.zync.chat.server.session.Group;
import com.zync.chat.server.session.GroupSession;
import com.zync.chat.server.session.GroupSessionFactory;
import com.zync.chat.common.utils.StringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 创建群聊 handler
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/8 17:01
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        // 群管理器
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Group group = groupSession.createGroup(groupName, members);
        if (Objects.isNull(group)) {
            // 群聊创建成功，发送群聊创建成功消息
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, StringUtil.format("群聊【{}】创建成功", groupName)));
            // 给相关人员发送拉取消息
            List<Channel> channels = groupSession.getMembersChannel(groupName);
            channels.forEach(channel -> channel.writeAndFlush(new GroupCreateResponseMessage(true, StringUtil.format("您已被拉入群聊【{}】", groupName))));
        } else {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, StringUtil.format("群聊【{}】已存在", groupName)));
        }
    }
}
