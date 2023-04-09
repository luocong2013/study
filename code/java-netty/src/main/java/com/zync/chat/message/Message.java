package com.zync.chat.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义消息-顶层抽象类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 13:53
 */
@Data
public abstract class Message implements Serializable {

    public static Class<? extends Message> getMessageClass(int messageType) {
        return MESSAGE_CLASSES.get(messageType);
    }

    /**
     * 消息序列ID
     */
    private int sequenceId;
    /**
     * 消息类型
     */
    private int messageType;

    /**
     * 获取消息类型
     * @return
     */
    public abstract int getMessageType();

    public static final int LOGIN_REQUEST_MESSAGE = 0;
    public static final int LOGIN_RESPONSE_MESSAGE = 1;
    public static final int CHAT_REQUEST_MESSAGE = 2;
    public static final int CHAT_RESPONSE_MESSAGE = 3;
    public static final int GROUP_CREATE_REQUEST_MESSAGE = 4;
    public static final int GROUP_CREATE_RESPONSE_MESSAGE = 5;
    public static final int GROUP_JOIN_REQUEST_MESSAGE = 6;
    public static final int GROUP_JOIN_RESPONSE_MESSAGE = 7;
    public static final int GROUP_QUIT_REQUEST_MESSAGE = 8;
    public static final int GROUP_QUIT_RESPONSE_MESSAGE = 9;
    public static final int GROUP_CHAT_REQUEST_MESSAGE = 10;
    public static final int GROUP_CHAT_RESPONSE_MESSAGE = 11;
    public static final int GROUP_MEMBERS_REQUEST_MESSAGE = 12;
    public static final int GROUP_MEMBERS_RESPONSE_MESSAGE = 13;
    public static final int GROUP_REMOVE_REQUEST_MESSAGE = 14;
    public static final int GROUP_REMOVE_RESPONSE_MESSAGE = 15;
    public static final int PING_MESSAGE = 16;
    public static final int PONG_MESSAGE = 17;
    private static final Map<Integer, Class<? extends Message>> MESSAGE_CLASSES = new HashMap<>();

    static {
        MESSAGE_CLASSES.put(LOGIN_REQUEST_MESSAGE, LoginRequestMessage.class);
        MESSAGE_CLASSES.put(LOGIN_RESPONSE_MESSAGE, LoginResponseMessage.class);
        MESSAGE_CLASSES.put(CHAT_REQUEST_MESSAGE, ChatRequestMessage.class);
        MESSAGE_CLASSES.put(CHAT_RESPONSE_MESSAGE, ChatResponseMessage.class);
        MESSAGE_CLASSES.put(GROUP_CREATE_REQUEST_MESSAGE, GroupCreateRequestMessage.class);
        MESSAGE_CLASSES.put(GROUP_CREATE_RESPONSE_MESSAGE, GroupCreateResponseMessage.class);
        MESSAGE_CLASSES.put(GROUP_JOIN_REQUEST_MESSAGE, GroupJoinRequestMessage.class);
        MESSAGE_CLASSES.put(GROUP_JOIN_RESPONSE_MESSAGE, GroupJoinResponseMessage.class);
        MESSAGE_CLASSES.put(GROUP_QUIT_REQUEST_MESSAGE, GroupQuitRequestMessage.class);
        MESSAGE_CLASSES.put(GROUP_QUIT_RESPONSE_MESSAGE, GroupQuitResponseMessage.class);
        MESSAGE_CLASSES.put(GROUP_CHAT_REQUEST_MESSAGE, GroupChatRequestMessage.class);
        MESSAGE_CLASSES.put(GROUP_CHAT_RESPONSE_MESSAGE, GroupChatResponseMessage.class);
        MESSAGE_CLASSES.put(GROUP_MEMBERS_REQUEST_MESSAGE, GroupMembersRequestMessage.class);
        MESSAGE_CLASSES.put(GROUP_MEMBERS_RESPONSE_MESSAGE, GroupMembersResponseMessage.class);
        MESSAGE_CLASSES.put(GROUP_REMOVE_REQUEST_MESSAGE, GroupRemoveRequestMessage.class);
        MESSAGE_CLASSES.put(GROUP_REMOVE_RESPONSE_MESSAGE, GroupRemoveResponseMessage.class);
        MESSAGE_CLASSES.put(PING_MESSAGE, PingMessage.class);
        MESSAGE_CLASSES.put(PONG_MESSAGE, PongMessage.class);
    }
}
