package com.zync.reactor;

import reactor.core.publisher.Flux;

/**
 * 以编程方式创建序列-Sink
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/12/31 15:49
 */
public class ReactorSink {

    public static void main(String[] args) {

        Flux.generate(() -> 0, // 初始值
                (state, sink) -> {
                    if (state <= 10) {
                        sink.next("2 * " + state + " = " + 2 * state); // 把元素传出去
                    } else {
                        sink.complete(); // 完成信号
                    }
                    return state + 3;
                }).subscribe(System.out::println);

    }
}
