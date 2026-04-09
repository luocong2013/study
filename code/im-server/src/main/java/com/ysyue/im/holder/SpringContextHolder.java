package com.ysyue.im.holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Spring Context配置
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/9 13:16
 **/
@Component
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(SpringContextHolder.class);
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        if (SpringContextHolder.context != null) {
            log.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为: {}", SpringContextHolder.context);
        }
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        assertContextInjected();
        return context;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        assertContextInjected();
        return (T) context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return context.getBean(requiredType);
    }

    public static String getProperty(String key) {
        assertContextInjected();
        return context.getEnvironment().getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        assertContextInjected();
        return context.getEnvironment().getProperty(key, defaultValue);
    }

    /**
     * 获取当前环境
     */
    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if (context == null) {
            throw new IllegalStateException("ApplicationContext属性未注入, 请在SpringBoot启动类中注册SpringContextHolder");
        }
    }

    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        log.debug("清除SpringContextHolder中的ApplicationContext: {}", context);
        context = null;
    }
}
