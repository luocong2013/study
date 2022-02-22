package com.zync.eight.util;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Assertion utility class that assists in validating arguments.
 *
 * @author luocong
 * @version v1.0
 * @date 2022/2/22 17:33
 */
public final class Assert {

    /**
     * Assert a boolean expression, throwing an {@code X}
     * if the expression evaluates to {@code false}.
     * <pre class="code">Assert.isTrue(i &gt; 0, () -> new RuntimeException("The value must be greater than zero"));</pre>
     * @param expression a boolean expression
     * @param supplier a supplier for the exception to use if the assertion fails
     * @throws X if {@code expression} is {@code false}
     */
    public static <X extends Throwable> void isTrue(boolean expression, Supplier<? extends X> supplier) throws X {
        if (!expression) {
            throw supplier.get();
        }
    }

    /**
     * Assert that an object is {@code null}.
     * <pre class="code">Assert.isNull(value, () -> new RuntimeException("The value must be null"));</pre>
     * @param object the object to check
     * @param supplier a supplier for the exception to use if the assertion fails
     * @throws X if the object is not {@code null}
     */
    public static <X extends Throwable> void isNull(Object object, Supplier<? extends X> supplier) throws X {
        if (Objects.nonNull(object)) {
            throw supplier.get();
        }
    }

    /**
     * Assert that an object is not {@code null}.
     * <pre class="code">Assert.notNull(clazz, () -> new RuntimeException("The class must not be null"));</pre>
     * @param object the object to check
     * @param supplier a supplier for the exception to use if the assertion fails
     * @throws X if the object is {@code null}
     */
    public static <X extends Throwable> void notNull(Object object, Supplier<? extends X> supplier) throws X {
        if (Objects.isNull(object)) {
            throw supplier.get();
        }
    }

    private Assert() {
    }
}
