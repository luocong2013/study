package com.zync.eight.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * java 8 function
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/30 17:48
 */
public final class FunctionUtil {

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


    private FunctionUtil() {
    }

}
