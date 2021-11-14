package com.zync.eight;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;

/**
 * 练习2
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/14 17:35
 */
public class Test2 {

    public static void main(String[] args) {
        Instant instant = Instant.now(Clock.systemDefaultZone());
        System.out.println(instant);

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        localDate = LocalDate.of(2021, 11, 15);
        System.out.println(localDate);
        // 获得当前日是当年的第几天
        System.out.println(localDate.getDayOfYear());
        // 是否是闰年
        System.out.println(localDate.isLeapYear());
        // 获得当年有多少天
        System.out.println(localDate.lengthOfYear());
        // 获得星期信息
        System.out.println(localDate.getDayOfWeek());
    }
}
