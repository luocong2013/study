package com.zync.eight;

import com.zync.eight.pojo.Apple;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 练习1
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/14 16:55
 */
public class Test1 {

    public static void main(String[] args) {

        // 1. Predicate<T>
        Predicate<String> predicate = s -> s.length() > 3;
        System.out.println(predicate.test("hello"));
        System.out.println(predicate.test("hi"));

        // 2. Consumer<T>
        Consumer<Apple> consumer = apple -> System.out.printf("The Apple Weights %s grams%n", apple.getWeight());
        consumer.accept(new Apple(30));

        // 3. Supplier<T>
        Supplier<Apple> supplier = () -> new Apple(290);
        consumer.accept(supplier.get());
    }
}
