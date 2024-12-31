package com.zync.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 错误处理
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/12/31 16:45
 */
public class ReactorError {

    public static void doOnError(String[] args) {
        // 异常被捕获、做自己的事情
        // 不吃掉异常，只在异常发生的时候做一件事，消费者有感知
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .doOnError(err -> System.out.println("err已被记录: " + err))
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    public static void main(String[] args) {
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorStop()
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    public static void onErrorComplete(String[] args) {
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorComplete()
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    public static void onErrorContinue(String[] args) {
        // 异常被捕获、做自己的事情
        // 不影响异常继续顺着流水线传播
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorContinue((err, val) -> {
                    System.out.println("err = " + err);
                    System.out.println("val = " + val);
                    System.out.println("发现"+val+"有问题了，继续执行其他的，我会记录这个问题");
                })
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    public static void onErrorMap(String[] args) {
        // onErrorMap
        // 1、吃掉异常并返回异常，消费者有感知
        // 2、调用一个自定义方法
        // 3、流异常完成
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorMap(err -> new RuntimeException(err.getMessage() + ": RuntimeException2"))
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    public static void onErrorResume2(String[] args) {
        // onErrorResume
        // 1、吃掉异常并返回异常，消费者有感知
        // 2、调用一个自定义方法
        // 3、流异常完成
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorResume(err -> Flux.error(new RuntimeException(err.getMessage() + ": RuntimeException")))
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    public static void onErrorResume(String[] args) {
        // onErrorResume
        // 1、吃掉异常，消费者无异常感知
        // 2、调用一个兜底方法
        // 3、流正常完成
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorResume(err -> Mono.just("just出错了"))
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }

    public static void onErrorReturn(String[] args) {
        // onErrorReturn: 错误的时候返回一个值
        // 1、吃掉异常，消费者无异常感知
        // 2、返回一个兜底默认值
        // 3、流正常完成；
        Flux.just(1, 2, 0, 4)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorReturn("出错了")
                .subscribe(v -> System.out.println("v = " + v),
                        err -> System.out.println("err = " + err),
                        () -> System.out.println("流结束"));
    }
}
