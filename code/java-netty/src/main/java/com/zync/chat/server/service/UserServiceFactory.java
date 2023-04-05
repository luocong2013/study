package com.zync.chat.server.service;

/**
 * 用户管理工厂类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 22:11
 */
public abstract class UserServiceFactory {

    private static final UserService USER_SERVICE = new UserServiceMemoryImpl();

    public static UserService getUserService() {
        return USER_SERVICE;
    }
}
