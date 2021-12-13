package com.zync.reflect;

import com.zync.reflect.domain.User;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Bean工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/13 09:50
 */
public final class BeanInfoUtil {

    private BeanInfoUtil() {
    }

    /**
     * 设置Bean的某个属性值
     * @param user
     * @param propertyName
     * @param value
     * @throws Exception
     */
    public static void setProperty(User user, String propertyName, Object value) throws Exception {
        // 获取Bean的某个属性的描述符
        PropertyDescriptor descriptor = new PropertyDescriptor(propertyName, user.getClass());
        // 获取用于写入属性值的方法
        Method method = descriptor.getWriteMethod();
        // 写入属性值
        method.invoke(user, value);
    }

    /**
     * 获取Bean的某个属性值
     * @param user
     * @param propertyName
     * @return
     * @throws Exception
     */
    public static Object getProperty(User user, String propertyName) throws Exception {
        // 获取Bean的某个属性的描述符
        PropertyDescriptor descriptor = new PropertyDescriptor(propertyName, user.getClass());
        // 获取用于读取属性值的方法
        Method method = descriptor.getReadMethod();
        // 读取属性值
        return method.invoke(user);
    }

    /**
     * 设置Bean的某个属性值，通过Introspector
     * @param user
     * @param propertyName
     * @param value
     * @throws Exception
     */
    public static void setPropertyByIntrospector(User user, String propertyName, Object value) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(user.getClass());
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        if (descriptors == null || descriptors.length <= 0) {
            return;
        }
        for (PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getName().equals(propertyName)) {
                Method method = descriptor.getWriteMethod();
                method.invoke(user, value);
            }
        }
    }

    /**
     * 获取Bean的某个属性值，通过Introspector
     * @param user
     * @param propertyName
     * @return
     * @throws Exception
     */
    public static Object getPropertyByIntrospector(User user, String propertyName) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(user.getClass());
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        if (descriptors == null || descriptors.length <= 0) {
            return null;
        }
        for (PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getName().equals(propertyName)) {
                Method method = descriptor.getReadMethod();
                return method.invoke(user);
            }
        }
        return null;
    }
}
