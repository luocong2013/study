package com.customzied.common.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.experimental.UtilityClass;

/**
 * trace id 生成工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/10/31 15:47
 */
@UtilityClass
public class LogTraceIdUtil {

    /**
     * 生成19位追踪ID
     *
     * @return ID
     */
    public String generateTraceId() {
        return IdWorker.getIdStr();
    }

}
