package com.zync.eight.function;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * java 8 function
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/30 17:48
 */
public final class FunctionUtil {


    /**
     * 获取对象属性值，为空返回 {@code null}
     * @param t
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R defaultIfNull(T t, Function<T, R> function) {
        return defaultIfNull(t, function, null);
    }

    /**
     * 获取对象属性值，为空返回 {@code nullDefault}
     * @param t 对象
     * @param function 转换函数
     * @param nullDefault 默认值
     * @param <T> 原始类型
     * @param <R> 结果类型
     * @return 结果
     */
    public static <T, R> R defaultIfNull(T t, Function<T, R> function, R nullDefault) {
        if (t == null) {
            return nullDefault;
        }
        Objects.requireNonNull(function);
        R r = function.apply(t);
        return r != null ? r : nullDefault;
    }

    /**
     * 获取满足条件的对象属性值，否则返回 {@code null}
     * @param t 对象
     * @param predicate 条件函数
     * @param function  转换函数
     * @param <T> 原始类型
     * @param <R> 结果类型
     * @return 结果
     */
    public static <T, R> R defaultIfNull(T t, Predicate<T> predicate, Function<T, R> function) {
        return defaultIfNull(t, predicate, function, null);
    }

    /**
     * 获取满足条件的对象属性值，否则返回 {@code nullDefault}
     * @param t 对象
     * @param predicate 条件函数
     * @param function  转换函数
     * @param nullDefault 默认值
     * @param <T> 原始类型
     * @param <R> 结果类型
     * @return 结果
     */
    public static <T, R> R defaultIfNull(T t, Predicate<T> predicate, Function<T, R> function, R nullDefault) {
        if (t == null) {
            return nullDefault;
        }
        Objects.requireNonNull(predicate);
        return predicate.test(t) ? defaultIfNull(t, function, nullDefault) : nullDefault;
    }

    /**
     * 查找集合中指定ID对应的属性
     * @param id ID
     * @param collection 集合
     * @param functionId 获取对象ID函数
     * @param functionValue 获取对象属性函数
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R findAttributes(Long id, Collection<T> collection, Function<T, Long> functionId, Function<T, R> functionValue) {
        if (id == null || collection == null || collection.isEmpty()) {
            return null;
        }
        return collection.parallelStream()
                .filter(t -> Objects.equals(id, defaultIfNull(t, functionId)))
                .findFirst()
                .map(t -> defaultIfNull(t, functionValue))
                .orElse(null);
    }

    /**
     * list 分页后调用 consumer
     *
     * @param list
     * @param size
     * @param consumer
     * @param <T>
     */
    public static <T> void pageCall(List<T> list, int size, Consumer<List<T>> consumer) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        int total = list.size();
        int pages = (total % size) == 0 ? (total / size) : (total / size) + 1;
        for (int i = 0; i < pages; i++) {
            List<T> temp = list.stream().skip((long) i * size).limit(size).collect(Collectors.toList());
            consumer.accept(temp);
        }
    }

    /**
     * list 分页后调用 function 并返回结果集
     *
     * @param list
     * @param size
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> pageCallVal(List<T> list, int size, Function<List<T>, R> function) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        int total = list.size();
        int pages = (total % size) == 0 ? (total / size) : (total / size) + 1;
        List<R> result = new ArrayList<>();
        for (int i = 0; i < pages; i++) {
            List<T> temp = list.stream().skip((long) i * size).limit(size).collect(Collectors.toList());
            R r = function.apply(temp);
            if (r != null) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * list 分页后调用 function 并返回结果集
     *
     * @param list
     * @param size
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> pageCallMulVal(List<T> list, int size, Function<List<T>, List<R>> function) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        int total = list.size();
        int pages = (total % size) == 0 ? (total / size) : (total / size) + 1;
        List<R> result = new ArrayList<>();
        for (int i = 0; i < pages; i++) {
            List<T> temp = list.stream().skip((long) i * size).limit(size).collect(Collectors.toList());
            List<R> r = function.apply(temp);
            if (CollectionUtils.isNotEmpty(r)) {
                result.addAll(r);
            }
        }
        return result;
    }


    private FunctionUtil() {
    }

}
