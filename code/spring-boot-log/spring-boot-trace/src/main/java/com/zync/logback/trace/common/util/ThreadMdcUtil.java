package com.zync.logback.trace.common.util;

import com.zync.logback.trace.common.Const;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * 线程MDC包装类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/25 12:08
 */
public final class ThreadMdcUtil {

    /**
     * 包装Runnable
     * @param runnable Runnable
     * @param context ContextMap
     * @return Runnable
     */
    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (Objects.isNull(context)) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

    /**
     * 包装Callable
     * @param callable Callable
     * @param context ContextMap
     * @param <T> T
     * @return Callable
     */
    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (Objects.isNull(context)) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    /**
     * set trace id if absent
     */
    private static void setTraceIdIfAbsent() {
        if (StringUtils.isBlank(MDC.get(Const.MDC_TRACE_ID))) {
            MDC.put(Const.MDC_TRACE_ID, LogTraceHelper.getTraceId());
        }
    }

    private ThreadMdcUtil() {}
}
