package com.customzied.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Context 配置
 *
 * @author luocong
 * @version v1.0
 * @date 2023/11/1 11:52
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextHolder.context != null) {
            log.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为: {}", SpringContextHolder.context);
        }
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        assertContextInjected();
        return context;
    }

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


    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if (context == null) {
            throw new IllegalStateException("ApplicationContext属性未注入, 请在SpringBoot启动类中注册SpringContextHolder");
        }
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        log.debug("清除SpringContextHolder中的ApplicationContext: {}", context);
        context = null;
    }
}
