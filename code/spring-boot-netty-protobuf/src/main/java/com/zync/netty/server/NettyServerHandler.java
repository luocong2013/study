package com.zync.netty.server;

import com.zync.netty.protobuf.UserData.UserMessage;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luocong
 * @version V1.0.0
 * @description 服务端处理器
 * @date 2020/6/3 17:14
 */
@Slf4j
@Sharable
@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 记录通道连接数
     */
    private static final AtomicInteger channelCounter = new AtomicInteger(0);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // ① 加入
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        // ② 注册
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // ③ 激活
        int count = channelCounter.incrementAndGet();
        log.info("Connects with {} as the {}th channel.", ctx.channel(), count);
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // ④ 读完成
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // ⑤ 读
        try {
            if (msg instanceof UserMessage) {
                UserMessage message = (UserMessage) msg;
                if (message.getState() == 1) {
                    log.info("客户端业务处理成功！");
                } else if (message.getState() == 2) {
                    log.info("接受到客户端发送的心跳！");
                } else {
                    log.info("未知命令！");
                }
            } else {
                log.info("未知数据！[{}]", msg);
            }
        } catch (Exception e) {
            log.error("读取数据出处", e);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // ⑥ 掉线
        int count = channelCounter.getAndDecrement();
        log.warn("Disconnects with {} as the {}th channel.", ctx.channel(), count);
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        // ⑦ 取消注册
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // ⑧ 离开
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 超时处理 如果10秒钟没有接受客户端的心跳，就触发，直接关闭;
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 超时处理
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 如果读通道处于空闲状态，说明没有接收到心跳命令
            if (IdleState.READER_IDLE.equals(event.state())) {
                log.info("已经10分钟没有接收到客户端的信息了，关闭通道 [{}].", ctx.channel());
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }
}
