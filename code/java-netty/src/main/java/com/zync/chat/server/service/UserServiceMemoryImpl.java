package com.zync.chat.server.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户管理接口实现类-基于内存
 *
 * @author luocong
 * @version v1.0
 * @date 2023/4/5 22:10
 */
public class UserServiceMemoryImpl implements UserService {

    private static final Map<String, String> ALL_USER_MAP = new ConcurrentHashMap<>(16);

    static {
        ALL_USER_MAP.put("zhangsan", "123");
        ALL_USER_MAP.put("lisi", "123");
        ALL_USER_MAP.put("wangwu", "123");
        ALL_USER_MAP.put("zhaoliu", "123");
        ALL_USER_MAP.put("qianqi", "123");
    }

    @Override
    public boolean login(String username, String password) {
        String pass = ALL_USER_MAP.get(username);
        if (pass == null) {
            return false;
        }
        return pass.equals(password);
    }
}
