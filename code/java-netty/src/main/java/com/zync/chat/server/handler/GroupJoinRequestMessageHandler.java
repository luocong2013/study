package com.zync.chat.server.handler;

import com.zync.chat.message.GroupJoinRequestMessage;
import com.zync.chat.message.GroupJoinResponseMessage;
import com.zync.chat.server.session.Group;
import com.zync.chat.server.session.GroupSessionFactory;
import com.zync.chat.utils.StringUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;

/**
 * 加入群聊 handler
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/8 17:03
 */
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Group group = GroupSessionFactory.getGroupSession().joinMember(groupName, msg.getUsername());
        if (Objects.nonNull(group)) {
            // 加入群聊成功
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, StringUtil.format("您已成功加入群聊【{}】", groupName)));
        } else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(false, StringUtil.format("群聊【{}】不存在", groupName)));
        }
    }
}
