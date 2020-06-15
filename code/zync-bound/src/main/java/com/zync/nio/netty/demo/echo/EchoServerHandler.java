package com.zync.nio.netty.demo.echo;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端 channel.
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // ① 加入
        System.out.println(this);
        System.out.println("handlerAdded....");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        // ② 注册
        super.channelRegistered(ctx);
        System.out.println("channelRegistered....");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // ③ 激活
        super.channelActive(ctx);
        System.out.println("channelActive....");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // ④ 读完成
        super.channelReadComplete(ctx);
        System.out.println("channelReadComplete....");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // ⑤ 读
        System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
        ctx.writeAndFlush("\nresponse: " + msg + "\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // ⑥ 掉线
        super.channelInactive(ctx);
        System.out.println("channelInactive....");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        // ⑦ 取消注册
        super.channelUnregistered(ctx);
        System.out.println("channelUnregistered....");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // ⑧ 离开
        System.out.println("handlerRemoved....");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 超时处理
        super.userEventTriggered(ctx, evt);
        System.out.println("userEventTriggered....");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        System.out.println("channelWritabilityChanged....");
    }
}