package com.zync.eight;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

/**
 * 练习6
 * 时间差
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/14 17:59
 */
public class Test6 {

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(1994, 11, 29);
        LocalDate now = LocalDate.now();
        Period period = Period.between(date, now);

        System.out.printf("相差: %d 年 %d 月 %d 天%n", period.getYears(), period.getMonths(), period.getDays());

        LocalTime time = LocalTime.of(10, 48, 39);
        LocalTime nowTime = LocalTime.now();
        Duration duration = Duration.between(time, nowTime);
        System.out.printf("相差: %d 分钟%n", duration.getSeconds());
    }
}
