package com.zync.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

/**
 * reactor subscribe测试
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/12/18 10:09
 */
public class ReactorSubscribe {

    public static void main(String[] args) {
        Flux<Integer> flux = Flux.range(1, 10)
                .doOnNext(integer -> {
                    if (integer == 6) {
                        integer = integer / (integer - 6);
                    }
                }).doOnError(throwable -> System.out.println("flux throwable = " + throwable))
                .doOnCancel(() -> System.out.println("flux 取消"));

        flux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(1);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value = " + value);
                if (value == 4) {
                    cancel();
                } else {
                    request(1);
                }
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("正常完成");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println("throwable = " + throwable);
            }

            @Override
            protected void hookOnCancel() {
                System.out.println("取消");
            }

            @Override
            protected void hookFinally(SignalType type) {
                System.out.println("结束，type = " + type);
            }
        });
    }

    public static void subscribe1() {
        Flux<Integer> flux = Flux.range(1, 10)
                .doOnNext(integer -> {
                    if (integer == 4) {
                        integer = integer / (integer - 4);
                    }
                }).doOnError(throwable -> System.out.println("flux throwable = " + throwable));
        flux.subscribe(integer -> System.out.println("integer = " + integer),
                throwable -> System.out.println("throwable = " + throwable),
                () -> System.out.println("正常完成"));
    }
}
