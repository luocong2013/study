package com.zync.logback.kafka.logging;

import ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.CoreConstants;

/**
 * Copied from spring-boot-xxx.jar
 * {@link ExtendedThrowableProxyConverter} that adds some additional whitespace around the stack trace.
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/26 17:36
 */
public class ExtendedWhitespaceThrowableProxyConverter extends ExtendedThrowableProxyConverter {

    @Override
    protected String throwableProxyToString(IThrowableProxy tp) {
        return "==>" + CoreConstants.LINE_SEPARATOR + super.throwableProxyToString(tp) + "<==" + CoreConstants.LINE_SEPARATOR + CoreConstants.LINE_SEPARATOR;
    }

}
