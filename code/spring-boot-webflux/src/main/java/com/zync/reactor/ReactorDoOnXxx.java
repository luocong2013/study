package com.zync.reactor;

import reactor.core.publisher.Flux;

/**
 * reactor doOnXxx测试
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/12/17 18:51
 */
public class ReactorDoOnXxx {

    public static void main(String[] args) {
        Flux<Integer> flux = Flux.range(1, 10)
                //.log()
                .doOnSubscribe(subscription -> System.out.println("建立连接: " + subscription))
                .doOnRequest(value -> System.out.println("value = " + value))
                .doOnNext(integer -> System.out.println("integer = " + integer))
                .doOnEach(integerSignal -> System.out.println("integerSignal = " + integerSignal))
                .doOnCancel(() -> System.out.println("取消"))
                .doOnError(throwable -> System.out.println("throwable = " + throwable))
                .doOnComplete(() -> System.out.println("完成"))
                .doOnTerminate(() -> System.out.println("终止"));
        flux.subscribe(System.out::println);
    }
}
