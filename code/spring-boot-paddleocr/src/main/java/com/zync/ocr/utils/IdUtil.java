package com.zync.ocr.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * ID生成器
 *
 * @author luocong
 * @version v2.2.1
 * @since 2024/7/1 16:56
 */
@UtilityClass
public class IdUtil {

    public String randomId() {
        return UUID.randomUUID().toString();
    }

}
