package com.zync.datetime;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * 时间日期工具测试类
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/9/20 17:01
 */
public class Client {

    public static void main(String[] args) throws Exception {
        Date date = DateUtils.parseDate("2024-09-20 10:20:30", "yyyy-MM-dd HH:mm:ss");

        String formatPattern = "yyyy-MM-dd HH:mm:ss.SSS";

        Date plusDate = DateTimeUtil.plusDays(date, 11);
        System.out.println(DateFormatUtils.format(plusDate, formatPattern));

        Date minusDate = DateTimeUtil.minusDays(date, 11);
        System.out.println(DateFormatUtils.format(minusDate, formatPattern));

        Date startDate = DateTimeUtil.getStartDay(date);
        System.out.println(DateFormatUtils.format(startDate, formatPattern));

        Date endDate = DateTimeUtil.getEndDay(date);
        System.out.println(DateFormatUtils.format(endDate, formatPattern));

        Date endDateToSecond = DateTimeUtil.getEndDayToSecond(date);
        System.out.println(DateFormatUtils.format(endDateToSecond, formatPattern));
    }
}
