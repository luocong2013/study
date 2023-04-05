package com.zync.chat.server.session;

/**
 * 会话管理工厂类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 22:25
 */
public abstract class SessionFactory {

    private static final Session SESSION = new SessionMemoryImpl();

    public static Session getSession() {
        return SESSION;
    }
}
