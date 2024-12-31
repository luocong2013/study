package com.zync.reactor;

import reactor.core.publisher.Flux;
import reactor.util.context.Context;

/**
 * Context-API: 响应式中的 ThreadLocal
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/12/31 17:03
 */
public class ReactorContextApi {

    public static void main(String[] args) {
        Flux.just(1, 2, 3)
                .transformDeferredContextual((flux, context) -> {
                    System.out.println("flux = " + flux);
                    System.out.println("context = " + context);
                    return flux.map(i -> i + "==>" + context.get("prefix"));
                })
                // 上游能拿到下游的最近一次数据
                .contextWrite(Context.of("prefix", "天齐大"))
                // ThreadLocal共享了数据，上游的所有人能看到; Context由下游传播给上游
                .subscribe(v -> System.out.println("v = " + v));
    }
}
