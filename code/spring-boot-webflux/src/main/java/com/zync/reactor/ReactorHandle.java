package com.zync.reactor;

import reactor.core.publisher.Flux;

/**
 * 自定义流中元素处理规则
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/12/31 15:55
 */
public class ReactorHandle {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .handle((value, sink) -> {
                    System.out.println("value = " + value);
                    sink.next("张三：" + value); // 可以向下发送数据的通道
                })
                .log()
                .subscribe();
    }
}
