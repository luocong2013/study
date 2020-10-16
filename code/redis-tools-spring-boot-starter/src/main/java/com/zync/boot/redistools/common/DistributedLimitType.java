package com.zync.boot.redistools.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author luocong
 * @version V1.0.0
 * @description 分布式限流类型
 * @date 2020/10/16 17:31
 */
@Getter
@AllArgsConstructor
public enum DistributedLimitType {
    /**
     * 基于时间窗口限流
     */
    TIME("/lua/TimeRateLimit.lua"),
    /**
     * 基于令牌桶限流
     */
    TOKEN_BUCKET("/lua/TokenBucketRateLimit.lua")
    ;

    /**
     * Lua脚本对应路径
     */
    private final String path;
}
