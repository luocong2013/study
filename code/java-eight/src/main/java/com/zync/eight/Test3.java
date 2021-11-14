package com.zync.eight;

import java.time.LocalTime;

/**
 * 练习3
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/14 17:47
 */
public class Test3 {

    public static void main(String[] args) {
        LocalTime now = LocalTime.now();
        System.out.println(now);

        LocalTime localTime = LocalTime.of(12, 20, 56);
        System.out.println(localTime);
    }
}
