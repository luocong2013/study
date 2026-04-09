package com.ysyue.im.util;

import lombok.experimental.UtilityClass;

/**
 * ID生成工具类
 *
 * @author luocong
 * @version 1.0
 * @since 2025/12/31 14:04
 **/
@UtilityClass
public class IdUtil {

    /**
     * 获取唯一ID
     *
     * @return id
     */
    public long getId() {
        return SnowflakeIdWorkerHolder.INSTANCE.nextId();
    }

    /**
     * 获取唯一ID
     *
     * @return id
     */
    public String getIdStr() {
        return SnowflakeIdWorkerHolder.INSTANCE.nextIdStr();
    }

    private static class SnowflakeIdWorkerHolder {
        private static final SnowflakeIdWorker INSTANCE = new SnowflakeIdWorker(0, 0);
    }

}
