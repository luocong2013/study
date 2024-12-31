package com.zync.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Parallel Flux 并发流
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/12/31 17:06
 */
public class ReactorRunOn {

    public static void main(String[] args) {
        Flux.range(1, 1000000)
                .buffer(100)
                .parallel(8)
                .runOn(Schedulers.newParallel("yy"))
                .log()
                .subscribe();
    }
}
