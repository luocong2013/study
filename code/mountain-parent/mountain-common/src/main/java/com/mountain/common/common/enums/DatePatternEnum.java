package com.mountain.common.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日期格式枚举
 *
 * @author luocong
 * @version v1.0
 * @since 2023/11/8 17:18
 */
@Getter
@AllArgsConstructor
public enum DatePatternEnum {
    /**
     * 标准时间格式
     */
    STANDARD_FORMAT("yyyy-MM-dd HH:mm:ss"),
    MINUTE_PATTERN("yyyy-MM-dd HH:mm"),
    DATE_TIME_SLASH_PATTERN("yyyy/MM/dd HH:mm:ss"),
    DATE_PATTERN("yyyy-MM-dd"),
    DATE_PATTERN_SLASH("yyyy/MM/dd"),
    DEFAULT_DATE_PATTERN("yyyyMMdd"),
    MONTH_PATTERN("yyyy-MM"),
    YEAR_PATTERN("yyyy"),
    MINUTE_ONLY_PATTERN("mm"),
    HOUR_ONLY_PATTERN("HH"),
    DATE_PATTERN_WITH_ZERO_TIME("yyyy-MM-dd 00:00:00"),
    PURE_DATETIME_PATTERN("yyyyMMddHHmmss");

    private final String pattern;
}
