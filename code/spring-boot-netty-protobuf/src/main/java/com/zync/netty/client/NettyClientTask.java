package com.zync.netty.client;

import com.zync.netty.protobuf.UserData.UserMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static com.zync.netty.protobuf.UserData.UserMessage.UserType.Admin;

/**
 * @author luocong
 * @version V1.0.0
 * @description 客户端任务
 * @date 2020/6/4 10:06
 */
@Slf4j
public class NettyClientTask implements Runnable {

    private Channel channel;

    public NettyClientTask(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            UserMessage message = NettyClientConst.buildUserMessage(NettyClientConst.getCounter(), Admin, UUID.randomUUID().toString(), "123", 22);
            channel.writeAndFlush(message);
        } catch (Exception e) {
            log.error("客户端任务执行失败", e);
        }
    }

}
