package com.zync.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * reactor 基于 reactive stream
 *
 * @author luocong
 * @version v1.0
 * @date 2023/2/19 18:59
 */
public class ReactorDemo {

    public static void main(String[] args) {
        // Mono 的创建
        // Mono 是 0-1 元素序列
        Mono.empty().subscribe(System.out::println);
        Mono.just("hello mono").subscribe(System.out::println);

        // Flux 的创建
        // Flux 是 0-N 元素序列
        Flux.just(1, 2, 3, 4, 5, 6, 7).subscribe(System.out::println);
        Flux.fromIterable(Arrays.asList("a", "b", "c")).subscribe(System.out::println);
        Flux.fromArray(new String[]{"a1", "b1", "c1"}).subscribe(System.out::println);
        Flux.fromStream(Stream.of("a2", "b2", "c2")).subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);

        Flux.generate(() -> 0, (i, sink) -> {
            sink.next("2*" + i + "=" + 2 * i);
            if (i == 9) {
                sink.complete();
            }
            return i + 1;
        }).subscribe(System.out::println);
    }
}
