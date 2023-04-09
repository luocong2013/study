package com.zync.chat.server.handler;

import com.zync.chat.message.GroupQuitRequestMessage;
import com.zync.chat.message.GroupQuitResponseMessage;
import com.zync.chat.server.session.Group;
import com.zync.chat.server.session.GroupSessionFactory;
import com.zync.chat.common.utils.StringUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;

/**
 * 退出群聊 handler
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/8 17:03
 */
@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Group group = GroupSessionFactory.getGroupSession().removeMember(groupName, msg.getUsername());
        if (Objects.nonNull(group)) {
            ctx.writeAndFlush(new GroupQuitResponseMessage(true, StringUtil.format("您已退出群聊【{}】", groupName)));
        } else {
            ctx.writeAndFlush(new GroupQuitResponseMessage(false, StringUtil.format("群聊【{}】不存在", groupName)));
        }
    }
}
