package com.zync.logback.trace.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * log trace helper
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/24 14:04
 */
public final class LogTraceHelper {

    /**
     * 获得trace id
     * @return String
     */
    public static String getTraceId() {
        return IdUtil.getSnowflakeNextIdStr();
    }

    private LogTraceHelper() {}
}
