package com.zync.datetime;

/**
 * java.time.* 测试类
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/11/13 10:30
 */
public class Java8Client {

    public static void main(String[] args) {
        System.out.println("2023年2月的天数: " + Java8DateUtil.getDaysInMonth(2023, 2));
        System.out.println("当前月份是第几季度: " + Java8DateUtil.getQuarter(Java8DateUtil.getCurrentDate()));
        System.out.println("当前月份工作日天数: " + Java8DateUtil.getWeekdayCountOfMonth(Java8DateUtil.getCurrentDate()));
        System.out.println("当前年工作日天数: " + Java8DateUtil.getWeekdayCountOfYear(Java8DateUtil.getCurrentDate()));
    }
}
