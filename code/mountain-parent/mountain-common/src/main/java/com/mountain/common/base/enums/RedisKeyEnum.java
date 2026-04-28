package com.mountain.common.base.enums;

import cn.hutool.core.text.StrPool;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * Redis的Key枚举
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/22 14:24
 **/
@Getter
@AllArgsConstructor
public enum RedisKeyEnum {

    AUTH_LOGIN_RSA("auth:login:rsa_", 2, TimeUnit.MINUTES, "RSA私钥Key"),
    AUTH_LOGIN_RSA_LOCK(AUTH_LOGIN_RSA.getKey("lock"), 0, null, "获取RSA公钥私钥加锁Key"),
    AUTH_LOGIN_RSA_KEY(AUTH_LOGIN_RSA.getKey("key"), 1, TimeUnit.DAYS, "RSA公钥和私钥Map的Key"),
    ;

    /**
     * 缓存key
     */
    private final String key;
    /**
     * 缓存过期时间
     */
    private final long expire;
    /**
     * 缓存过期时间单位
     */
    private final TimeUnit timeUnit;
    /**
     * 缓存描述
     */
    private final String description;

    /**
     * 获取缓存Key
     *
     * @param object 参数
     * @return 缓存Key
     */
    public String getKey(Object object) {
        if (object == null) {
            return key;
        }
        return key + object;
    }

    /**
     * 获取缓存Key
     *
     * @param objects 参数
     * @return 缓存Key
     */
    public String getKey(Object... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return key;
        }
        return key + StringUtils.joinWith(StrPool.UNDERLINE, objects);
    }
}
