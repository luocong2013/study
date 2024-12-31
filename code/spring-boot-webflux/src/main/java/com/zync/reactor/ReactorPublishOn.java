package com.zync.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * 自定义线程调度
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/12/31 15:57
 */
public class ReactorPublishOn {

    public static void main(String[] args) {
        // 响应式编程： 全异步、消息、事件回调
        // 默认还是用当前线程，生成整个流、发布流、流操作
        Scheduler scheduler = Schedulers.newParallel("customize-scheduler", 4);

        Flux<String> flux = Flux.range(1, 2)
                .map(i -> 10 + i)
                .log() // 在 customize-thread 线程
                .publishOn(scheduler)
                .log() // 在 customize-scheduler 线程池
                .map(i -> "value" + i);

        // 只要不指定线程池，默认发布者用的线程就是订阅者的线程；
        new Thread(() -> flux.subscribe(System.out::println), "customize-thread").start();

    }

}
