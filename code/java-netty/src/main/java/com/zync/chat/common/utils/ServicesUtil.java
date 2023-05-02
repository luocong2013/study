package com.zync.chat.common.utils;

import lombok.experimental.UtilityClass;

/**
 * 获取Service工厂类
 *
 * @author luocong
 * @version v1.0
 * @date 2023/5/2 22:44
 */
@UtilityClass
public class ServicesUtil {

    public <T> T getService(Class<T> interfaceClass) {
        try {
            String instanceClassName = PropertiesUtil.get(interfaceClass.getName());
            Class<?> instanceClass = Class.forName(instanceClassName);
            return (T) instanceClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("get service error", e);
        }
    }

}
