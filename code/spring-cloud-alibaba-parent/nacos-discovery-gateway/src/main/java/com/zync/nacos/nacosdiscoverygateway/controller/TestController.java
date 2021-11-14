package com.zync.nacos.nacosdiscoverygateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 测试
 *
 * @author luocong
 * @date 2021/7/23 11:35
 */
@RestController
public class TestController {

    @GetMapping("/hi/{username}")
    public Mono<String> hi(@PathVariable("username") String username, ServerRequest request, ServerResponse response) {
        return Mono.just("hi " + username);
    }


    public static void main(String[] args) {
        Flux.range(1, 10).timeout(Flux.never(), v -> Flux.never())
                .subscribe(System.out::println);
    }

}
