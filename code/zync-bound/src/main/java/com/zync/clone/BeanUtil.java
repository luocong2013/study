package com.zync.clone;

import net.sf.cglib.beans.BeanCopier;
import org.springframework.util.Assert;

/**
 * @author luocong
 */
public final class BeanUtil {

    private BeanUtil() {
    }

    /**
     * @param orig 复制源对象
     * @param dest 复制目的对象类
     * @Description: 对象浅复制
     * @return: T
     */
    public static <T> T shallowCopy(Object orig, Class<T> dest) {
        Assert.notNull(orig, "复制目标对象不能为空");
        try {
            BeanCopier copier = BeanCopier.create(orig.getClass(), dest, false);
            T instance = dest.newInstance();
            copier.copy(orig, instance, null);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
