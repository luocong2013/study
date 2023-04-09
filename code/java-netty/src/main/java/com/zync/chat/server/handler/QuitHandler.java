package com.zync.chat.server.handler;

import com.zync.chat.server.session.Session;
import com.zync.chat.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 退出 handler
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/9 15:38
 */
@Slf4j
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当连接断开时触发 inactive 事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        Session session = SessionFactory.getSession();
        String username = session.getUsername(channel);
        // 解绑会话
        session.unbind(channel);
        log.debug("{} 已经断开连接", username);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        Session session = SessionFactory.getSession();
        String username = session.getUsername(channel);
        // 解绑会话
        session.unbind(channel);
        log.debug("{} 已经异常断开连接, 异常信息是: {}", username, cause.getMessage());
    }
}
