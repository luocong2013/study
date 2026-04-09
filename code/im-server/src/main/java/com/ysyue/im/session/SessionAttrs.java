package com.ysyue.im.session;

import io.netty.util.AttributeKey;

/**
 * Session常量
 *
 * @author luocong
 * @version 1.0
 * @since 2026/3/24 13:05
 **/
public interface SessionAttrs {

    /**
     * 用户ID
     */
    AttributeKey<String> USER_ID = AttributeKey.valueOf("USER_ID");
}
