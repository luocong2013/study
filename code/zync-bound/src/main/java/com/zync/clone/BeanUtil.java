package com.zync.clone;

import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanCopier的copy
 *
 * @author luocong
 */
public final class BeanUtil {

    /**
     * BeanCopier的缓存
     */
    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>(16);

    /**
     * 使用BeanCopier的对象浅复制
     * @param source 复制源对象
     * @param target 复制目标对象的Class
     * @return T
     */
    public static <T> T shallowCopy(Object source, Class<T> target) {
        Assert.notNull(source, "source must not be null");
        Assert.notNull(target, "target class must not be null");
        String key = key(source.getClass(), target);
        BeanCopier beanCopier;
        if (BEAN_COPIER_CACHE.containsKey(key)) {
            beanCopier = BEAN_COPIER_CACHE.get(key);
        } else {
            beanCopier = BeanCopier.create(source.getClass(), target, false);
            BEAN_COPIER_CACHE.put(key, beanCopier);
        }
        try {
            T instance = target.newInstance();
            beanCopier.copy(source, instance, null);
            return instance;
        } catch (Exception ignore) {
        }
        return null;
    }

    /**
     * BeanCopier的copy
     * @param source 源对象
     * @param target 目标对象
     */
    public void shallowCopy(Object source, Object target) {
        Assert.notNull(source, "source must not be null");
        Assert.notNull(target, "target must not be null");
        String key = key(source.getClass(), target.getClass());
        BeanCopier beanCopier;
        if (BEAN_COPIER_CACHE.containsKey(key)) {
            beanCopier = BEAN_COPIER_CACHE.get(key);
        } else {
            beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
            BEAN_COPIER_CACHE.put(key, beanCopier);
        }
        beanCopier.copy(source, target, null);
    }

    /**
     * 生成key
     * @param source 源对象的Class
     * @param target 目标对象的Class
     * @param <T>
     * @return
     */
    private static <T> String key(Class<?> source, Class<T> target) {
        return StringUtils.joinWith("_", source.getName(), target.getName());
    }

    private BeanUtil() {
    }
}
