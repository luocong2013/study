package com.zync.ai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author admin
 * @version 1.0
 * @description
 * @since 2025/3/24 18:51
 **/
public class DateTimeTools {

    @Tool(description = "Get the current date and time in the user's timezone") // 根据当前日期时间2025年3月24日星期一，明天就是2025年3月25日，是星期二。
    // @Tool(description = "获取用户时区中的当前日期和时间") // 根据当前时间，今天是星期一。因此，明天是星期二。
    // @Tool // 根据当前日期时间，明天是星期二。
    public String getCurrentDateTime() {
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

    @Tool(description = "Set a user alarm for the given time, provided in ISO-8601 format")
    public void setAlarm(@ToolParam(description = "Time in ISO-8601 format") String time) {
        LocalDateTime alarmTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("Alarm set for " + alarmTime);
    }

}
