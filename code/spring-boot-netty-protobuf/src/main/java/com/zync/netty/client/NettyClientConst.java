package com.zync.netty.client;

import com.zync.netty.protobuf.UserData.UserMessage;
import com.zync.netty.protobuf.UserData.UserMessage.UserInfo.Basic;
import com.zync.netty.protobuf.UserData.UserMessage.UserType;
import io.netty.util.AttributeKey;
import lombok.experimental.UtilityClass;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luocong
 * @version V1.0.0
 * @description 客户端存储参数
 * @date 2020/6/4 10:01
 */
@UtilityClass
public class NettyClientConst {

    public final AttributeKey<ScheduledFuture<?>> CLIENT_CONTEXT_KEY = AttributeKey.valueOf("clientContext");

    private final AtomicInteger counter = new AtomicInteger(1);

    public int getCounter() {
        return counter.getAndIncrement();
    }

    public UserMessage buildUserMessage(int id, UserType userType, String username, String password, int age) {
        return UserMessage.newBuilder()
                .setState(1)
                .setUserType(userType)
                .setUserInfo(
                        UserMessage.UserInfo.newBuilder()
                                .setBasic(
                                        Basic.newBuilder()
                                                .setId(id)
                                                .setUsername(username)
                                                .setPassword(password)
                                                .setAge(age)
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
