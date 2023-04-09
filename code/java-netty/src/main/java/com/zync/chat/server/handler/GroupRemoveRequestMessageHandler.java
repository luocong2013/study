package com.zync.chat.server.handler;

import com.zync.chat.message.GroupRemoveRequestMessage;
import com.zync.chat.message.GroupRemoveResponseMessage;
import com.zync.chat.server.session.Group;
import com.zync.chat.server.session.GroupSession;
import com.zync.chat.server.session.GroupSessionFactory;
import com.zync.chat.server.session.SessionFactory;
import com.zync.chat.utils.StringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Objects;

/**
 * 删除群聊 handler
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 12:29
 */
@ChannelHandler.Sharable
public class GroupRemoveRequestMessageHandler extends SimpleChannelInboundHandler<GroupRemoveRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupRemoveRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        // 群管理器
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Group group = groupSession.removeGroup(groupName);
        if (Objects.nonNull(group)) {
            // 群聊删除成功
            ctx.writeAndFlush(new GroupRemoveResponseMessage(true, StringUtil.format("群聊【{}】删除成功", groupName)));
            // 给相关人员发送群聊解散消息
            List<Channel> channels = group.getMembers().stream()
                    .map(member -> SessionFactory.getSession().getChannel(member))
                    .filter(Objects::nonNull).toList();
            channels.forEach(channel -> channel.writeAndFlush(new GroupRemoveResponseMessage(true, StringUtil.format("群聊【{}】已被解散", groupName))));
        } else {
            ctx.writeAndFlush(new GroupRemoveResponseMessage(false, StringUtil.format("群聊【{}】不存在", groupName)));
        }
    }
}
