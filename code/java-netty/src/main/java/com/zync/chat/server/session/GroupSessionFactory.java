package com.zync.chat.server.session;

/**
 * 聊天组会话管理工厂类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 22:18
 */
public abstract class GroupSessionFactory {

    private static final GroupSession SESSION = new GroupSessionMemoryImpl();

    public static GroupSession getGroupSession() {
        return SESSION;
    }
}
