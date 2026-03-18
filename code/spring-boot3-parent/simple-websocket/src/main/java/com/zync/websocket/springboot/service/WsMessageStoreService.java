package com.zync.websocket.springboot.service;

import com.zync.websocket.springboot.model.WsMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * web socket 消息存储服务
 * 这里就用一个Map来存储用户的离线消息了，正常应该存入数据库或者缓存中
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/17 15:10
 **/
@Service
public class WsMessageStoreService {

    private static final CopyOnWriteArrayList<WsMessage> MESSAGES = new CopyOnWriteArrayList<>();

    public void save(WsMessage message) {
        MESSAGES.add(message);
    }

    public void updateMessageStatus(Long id, Integer status) {
        MESSAGES.stream().filter(message -> Objects.equals(message.getId(), id)).forEach(message -> message.setStatus(status));
    }

    public List<WsMessage> findUnPushByUserId(String userId) {
        return MESSAGES.stream().filter(message -> StringUtils.equals(message.getUserId(), userId) && !message.isPushed()).toList();
    }

    public WsMessage findById(Long id) {
        return MESSAGES.stream().filter(message -> Objects.equals(message.getId(), id)).findFirst().orElseThrow(() -> new RuntimeException("消息不存在"));
    }

}
