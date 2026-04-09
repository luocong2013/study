package com.ysyue.im.group;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 群聊管理器
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/23 9:54
 **/
@Slf4j
@Component
public class GroupManager {

    /**
     * 群组 ID -> 成员用户 ID 集合
     */
    private final Map<String, Set<String>> groupMembersMap = new ConcurrentHashMap<>();

    /**
     * 群组 ID -> 成员 Channel 集合
     */
    private final Map<String, Set<Channel>> groupChannelsMap = new ConcurrentHashMap<>();

    /**
     * 创建群组
     */
    public void createGroup(String groupId) {
        groupMembersMap.putIfAbsent(groupId, new CopyOnWriteArraySet<>());
        groupChannelsMap.putIfAbsent(groupId, new CopyOnWriteArraySet<>());
        log.info("群组 {} 创建成功", groupId);
    }

    /**
     * 加入群组
     */
    public void joinGroup(String groupId, String userId, Channel channel) {
        groupMembersMap.computeIfAbsent(groupId, k -> new CopyOnWriteArraySet<>()).add(userId);
        groupChannelsMap.computeIfAbsent(groupId, k -> new CopyOnWriteArraySet<>()).add(channel);
        log.info("用户 {} 加入群组 {}", userId, groupId);
    }

    /**
     * 退出群组
     */
    public void leaveGroup(String groupId, String userId, Channel channel) {
        Set<String> members = groupMembersMap.get(groupId);
        Set<Channel> channels = groupChannelsMap.get(groupId);

        if (members != null) {
            members.remove(userId);
        }
        if (channels != null) {
            channels.remove(channel);
        }

        log.info("用户 {} 退出群组 {}", userId, groupId);
    }

    /**
     * 获取群组所有成员的 Channel
     */
    public Set<Channel> getGroupChannels(String groupId) {
        return groupChannelsMap.getOrDefault(groupId, ConcurrentHashMap.newKeySet());
    }

    /**
     * 删除群组
     */
    public void deleteGroup(String groupId) {
        groupMembersMap.remove(groupId);
        groupChannelsMap.remove(groupId);
        log.info("群组 {} 已删除", groupId);
    }

    /**
     * 获取群组人数
     */
    public int getGroupSize(String groupId) {
        Set<String> members = groupMembersMap.get(groupId);
        return members != null ? members.size() : 0;
    }
}
