package com.mountain.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Assertion utility class that assists in validating arguments.
 *
 * @author luocong
 * @version v1.0
 * @since 2023/11/9 17:17
 */
@UtilityClass
public class Assert {

    /**
     * Assert a boolean expression, throwing an {@code X}
     * if the expression evaluates to {@code false}.
     * <pre class="code">Assert.isTrue(i &gt; 0, () -> new RuntimeException("The value must be greater than zero"));</pre>
     *
     * @param expression a boolean expression
     * @param supplier   a supplier for the exception to use if the assertion fails
     * @throws X if {@code expression} is {@code false}
     */
    public <X extends Throwable> void isTrue(boolean expression, Supplier<? extends X> supplier) throws X {
        if (!expression) {
            throw supplier.get();
        }
    }

    /**
     * Assert that an object is {@code null}.
     * <pre class="code">Assert.isNull(value, () -> new RuntimeException("The value must be null"));</pre>
     *
     * @param object   the object to check
     * @param supplier a supplier for the exception to use if the assertion fails
     * @throws X if the object is not {@code null}
     */
    public <X extends Throwable> void isNull(Object object, Supplier<? extends X> supplier) throws X {
        if (Objects.nonNull(object)) {
            throw supplier.get();
        }
    }

    /**
     * Assert that an object is not {@code null}.
     * <pre class="code">Assert.notNull(clazz, () -> new RuntimeException("The class must not be null"));</pre>
     *
     * @param object   the object to check
     * @param supplier a supplier for the exception to use if the assertion fails
     * @throws X if the object is {@code null}
     */
    public <X extends Throwable> void notNull(Object object, Supplier<? extends X> supplier) throws X {
        if (Objects.isNull(object)) {
            throw supplier.get();
        }
    }

    /**
     * Assert that an sequence is not blank.
     * <pre class="code">Assert.notNull(value, () -> new RuntimeException("The value must not be
     * blank"));</pre>
     *
     * @param sequence the sequence to check
     * @param supplier a supplier for the exception to use if the assertion fails
     * @throws X if the sequence is blank
     */
    public <X extends Throwable> void notBlank(CharSequence sequence, Supplier<? extends X> supplier) throws X {
        if (StringUtils.isBlank(sequence)) {
            throw supplier.get();
        }
    }

    /**
     * Assert that an collection is not empty.
     * <pre class="code">Assert.notNull(collection, () -> new RuntimeException("The collection must not be
     * empty"));</pre>
     *
     * @param collection the collection to check
     * @param supplier   a supplier for the exception to use if the assertion fails
     * @throws X if the collection is empty
     */
    public <X extends Throwable> void notEmpty(Collection<?> collection, Supplier<? extends X> supplier) throws X {
        if (collection == null || collection.isEmpty()) {
            throw supplier.get();
        }
    }
}
