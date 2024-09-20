package com.zync.datetime;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 时间日期工具类
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/9/20 16:42
 */
@UtilityClass
public class DateTimeUtil {

    /**
     * 后 days 天
     * @param date
     * @param days
     * @return
     */
    public Date plusDays(Date date, long days) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant().plus(days, ChronoUnit.DAYS);
        return Date.from(instant);
    }

    /**
     * 前 days 天
     * @param date
     * @param days
     * @return
     */
    public Date minusDays(Date date, long days) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant().minus(days, ChronoUnit.DAYS);
        return Date.from(instant);
    }

    /**
     * 获取一天的开始时间（到毫秒 即: 2024-09-20 00:00:00.000）
     * @param date
     * @return
     */
    public Date getStartDay(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime dateTime = dateToLocalDateTime(date);
        if (dateTime == null) {
            return null;
        }
        return localDateTimeToDate(dateTime.with(LocalTime.MIN));
    }

    /**
     * 获取一天的结束时间（到毫秒 即: 2024-09-20 23:59:59.999）
     * @param date
     * @return
     */
    public Date getEndDay(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime dateTime = dateToLocalDateTime(date);
        if (dateTime == null) {
            return null;
        }
        return localDateTimeToDate(dateTime.with(LocalTime.MAX));
    }

    /**
     * 获取一天的结束时间（到秒 即: 2024-09-20 23:59:59）
     * @param date
     * @return
     */
    public Date getEndDayToSecond(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime dateTime = dateToLocalDateTime(date);
        if (dateTime == null) {
            return null;
        }
        return localDateTimeToDate(dateTime.with(LocalTime.MAX.minus(999, ChronoUnit.MILLIS)));
        // 或者
        // return localDateTimeToDate(dateTime.with(LocalTime.MAX.minusNanos(999_999_999)));
    }

    /**
     * Date 转 LocalDateTime
     * @param date
     * @return
     */
    private LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime 转 Date
     * @param dateTime
     * @return
     */
    private Date localDateTimeToDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

}
