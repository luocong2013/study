package com.zync.reactor;

import reactor.core.publisher.Flux;

/**
 * reactor 测试2
 * @author luocong
 * @version v1.0
 * @date 2023/2/19 19:31
 */
public class ReactorDemo2 {

    public static void main(String[] args) {
        String src = "hello guys i am bole welcome to normal school jdk quick fox prizev";
        Flux.fromArray(src.split(" "))
                .flatMap(i -> Flux.fromArray(i.split("")))
                .distinct()
                .sort()
                .subscribe(System.out::print);
    }
}
