package com.ysyue.im.processor;

import com.ysyue.im.protocol.ImMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * IM 消息处理
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/9 11:12
 **/
public interface ImMessageProcessor {

    /**
     * 处理 IM 消息
     *
     * @param ctx 通道
     * @param message 消息
     */
    void processor(ChannelHandlerContext ctx, ImMessage message);

}
