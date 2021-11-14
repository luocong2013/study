package com.zync.eight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 练习5
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/14 17:54
 */
public class Test5 {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(formatter.format(now));

        String str = "2021年11月14日 17:56:10";
        LocalDateTime localDateTime = LocalDateTime.parse(str, formatter);
        System.out.println(localDateTime);
    }
}
