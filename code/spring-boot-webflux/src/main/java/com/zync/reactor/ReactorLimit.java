package com.zync.reactor;

import reactor.core.publisher.Flux;

/**
 * limit 限流
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/12/31 15:41
 */
public class ReactorLimit {

    public static void main(String[] args) {
        // 限流触发
        // 一次预取100个元素； 第一次 request(100)，以后request(75)
        Flux.range(1, 1000)
                .log()
                .limitRate(100)
                //.limitRate(100, 90)
                .subscribe();
    }
}
