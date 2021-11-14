package com.zync.eight;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * 练习4
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/14 17:51
 */
public class Test4 {

    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);

        Instant instant = Instant.now(Clock.systemDefaultZone());
        System.out.println(instant);

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }
}
