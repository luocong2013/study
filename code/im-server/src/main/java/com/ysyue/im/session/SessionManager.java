package com.ysyue.im.session;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理器
 * 管理用户与连接的映射关系
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/23 9:47
 **/
@Slf4j
@Component
public class SessionManager {

    /**
     * 用户 ID -> Channel 映射
     */
    private final Map<String, Channel> userChannelMap = new ConcurrentHashMap<>();

    /**
     * 绑定用户与连接
     */
    public void bind(String userId, Channel channel) {
        userChannelMap.put(userId, channel);
        channel.attr(SessionAttrs.USER_ID).set(userId);
        log.info("User {} bound to channel {}", userId, channel.id());
    }

    /**
     * 解除绑定
     */
    public void unbind(Channel channel) {
        String userId = channel.attr(SessionAttrs.USER_ID).get();
        if (userId != null) {
            userChannelMap.remove(userId);
            log.info("User {} unbound from channel {}", userId, channel.id());
        }
    }

    /**
     * 获取用户的 Channel
     */
    public Channel getChannel(String userId) {
        return userChannelMap.get(userId);
    }

    /**
     * 获取 Channel 对应的用户 ID
     */
    public String getUserId(Channel channel) {
        return channel.attr(SessionAttrs.USER_ID).get();
    }

    /**
     * 判断用户是否在线
     */
    public boolean isOnline(String userId) {
        Channel channel = userChannelMap.get(userId);
        return channel != null && channel.isActive();
    }

    /**
     * 获取在线用户数量
     */
    public int getOnlineCount() {
        return userChannelMap.size();
    }
}
