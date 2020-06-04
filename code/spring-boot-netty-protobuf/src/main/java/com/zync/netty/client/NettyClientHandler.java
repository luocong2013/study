package com.zync.netty.client;

import com.zync.netty.protobuf.UserData.UserMessage;
import com.zync.netty.protobuf.UserData.UserMessage.UserInfo.Basic;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luocong
 * @version V1.0.0
 * @description 客户端处理器
 * @date 2020/6/3 17:32
 */
@Slf4j
@Sharable
@Component
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private NettyClient nettyClient;

    @Autowired
    private NettyClientPool nettyClientPool;

    /** 循环次数 */
    private AtomicInteger count = new AtomicInteger(1);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("建立连接时间：" + new Date());
        nettyClientPool.executeTask(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("关闭连接时间：" + new Date());
        nettyClientPool.cancel(ctx.channel());
        final EventLoop eventLoop = ctx.channel().eventLoop();
        nettyClient.connect(new Bootstrap(), eventLoop);
        super.channelInactive(ctx);
    }

    /**
     * 心跳请求处理 每9秒发送一次心跳请求;
     *
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        log.info("循环请求的时间：" + new Date() + "，次数" + count.get());
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            // 如果写通道处于空闲状态,就发送心跳命令
            if (IdleState.WRITER_IDLE.equals(event.state())) {
                UserMessage message = UserMessage.newBuilder().setState(2).build();
                ctx.channel().writeAndFlush(message);
                count.getAndIncrement();
            }
        }
    }

    /**
     * 业务逻辑处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 如果不是protobuf类型的数据
        if (!(msg instanceof UserMessage)) {
            log.info("未知数据！[{}]", msg);
            return;
        }
        try {
            // 得到protobuf的数据
            UserMessage message = (UserMessage) msg;
            // 进行相应的业务处理。。。
            // 这里就从简了，只是打印而已
            Basic basic = message.getUserInfo().getBasic();
            log.info("客户端接受到的用户信息。编号: [{}]，姓名: [{}]，密码: [{}]，年龄：[{}]", basic.getId(), basic.getUsername(), basic.getPassword(), basic.getAge());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
