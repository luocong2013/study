package com.zync.chat.server.service;

/**
 * 用户管理接口
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 22:10
 */
public interface UserService {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回 true, 否则返回 false
     */
    boolean login(String username, String password);
}
