package com.zync.reactor;

import reactor.core.publisher.Flux;

import java.util.List;

/**
 * buffer 缓冲
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/12/31 15:35
 */
public class ReactorBuffer {

    public static void main(String[] args) {
        // 原始流10个
        // 缓冲区：缓冲3个元素: 消费一次最多可以拿到三个元素； 凑满数批量发给消费者
        Flux<List<Integer>> flux = Flux.range(1, 10)
                .buffer(3)
                .log();

        flux.subscribe(v -> System.out.println("v = " + v));
    }
}
