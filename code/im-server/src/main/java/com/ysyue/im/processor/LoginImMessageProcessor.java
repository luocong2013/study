package com.ysyue.im.processor;

import com.ysyue.im.protocol.ImMessage;
import com.ysyue.im.protocol.MessageType;
import com.ysyue.im.util.IdUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 处理登录消息
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/9 13:06
 **/
@Slf4j
@Component
public class LoginImMessageProcessor extends AbstractImMessageProcessor {

    @Override
    public void processor(ChannelHandlerContext ctx, ImMessage message) {
        String userId = message.getSenderId();

        // token 鉴权
        if (StringUtils.isBlank(userId)) {
            sendErrorResponse(ctx, "用户 ID 不能为空");
            return;
        }

        sessionManager.bind(userId, ctx.channel());

        ImMessage response = ImMessage.builder()
                .type(MessageType.LOGIN_RESPONSE)
                .messageId(IdUtil.getIdStr())
                .timestamp(System.currentTimeMillis())
                .ext("登录成功")
                .build();

        writeAndFlush(ctx, response);
        log.info("User {} logged in successfully", userId);
    }

}
