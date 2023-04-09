package com.zync.chat.server.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理接口实现类-基于内存
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 22:22
 */
public class SessionMemoryImpl implements Session {

    private final Map<String, Channel> usernameChannelMap = new ConcurrentHashMap<>(16);
    private final Map<Channel, String> channelUsernameMap = new ConcurrentHashMap<>(16);
    private final Map<Channel, Map<String, Object>> channelAttributesMap = new ConcurrentHashMap<>(16);

    @Override
    public void bind(Channel channel, String username) {
        usernameChannelMap.put(username, channel);
        channelUsernameMap.put(channel, username);
        channelAttributesMap.put(channel, new ConcurrentHashMap<>(4));
    }

    @Override
    public void unbind(Channel channel) {
        String username = channelUsernameMap.remove(channel);
        if (Objects.nonNull(username)) {
            usernameChannelMap.remove(username);
        }
        channelAttributesMap.remove(channel);
    }

    @Override
    public Object getAttribute(Channel channel, String name) {
        return channelAttributesMap.get(channel).get(name);
    }

    @Override
    public void setAttribute(Channel channel, String name, Object value) {
        channelAttributesMap.get(channel).put(name, value);
    }

    @Override
    public Channel getChannel(String username) {
        return usernameChannelMap.get(username);
    }

    @Override
    public String getUsername(Channel channel) {
        return channelUsernameMap.get(channel);
    }

    @Override
    public String toString() {
        return usernameChannelMap.toString();
    }
}
