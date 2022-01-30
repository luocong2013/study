package com.zync.boot.mybatisplus.ext.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.zync.boot.mybatisplus.ext.annotation.Default;
import org.apache.ibatis.reflection.MetaObject;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 默认值填充MetaObjectHandler
 *
 * @author luocong
 * @version v1.0
 * @date 2022/1/28 17:51
 */
public class DefaultMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        TableInfo tableInfo = findTableInfo(metaObject);
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        for (TableFieldInfo tableField : fieldList) {
            String fieldName = tableField.getProperty();
            if (Objects.nonNull(metaObject.getValue(fieldName))) {
                continue;
            }
            Field field = tableField.getField();
            Default annotation = field.getAnnotation(Default.class);
            if (Objects.isNull(annotation)) {
                continue;
            }
            Object object = obtainObject(tableField.getPropertyType(), annotation);
            if (Objects.nonNull(object)) {
                metaObject.setValue(fieldName, object);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

    /**
     * 获取默认值
     * @param propertyType Class
     * @param annotation Default
     * @return Object
     */
    private Object obtainObject(Class<?> propertyType, Default annotation) {
        if (propertyType.isAssignableFrom(Byte.class)) {
            return annotation.byteValue();
        }
        if (propertyType.isAssignableFrom(Short.class)) {
            return annotation.shortValue();
        }
        if (propertyType.isAssignableFrom(Character.class)) {
            return annotation.charValue();
        }
        if (propertyType.isAssignableFrom(Integer.class)) {
            return annotation.intValue();
        }
        if (propertyType.isAssignableFrom(Long.class)) {
            return annotation.longValue();
        }
        if (propertyType.isAssignableFrom(Float.class)) {
            return annotation.floatValue();
        }
        if (propertyType.isAssignableFrom(Double.class)) {
            return annotation.doubleValue();
        }
        if (propertyType.isAssignableFrom(Boolean.class)) {
            return annotation.booleanValue();
        }
        if (propertyType.isAssignableFrom(String.class)) {
            return annotation.stringValue();
        }
        if (propertyType.isAssignableFrom(Class.class)) {
            return annotation.classValue();
        }
        if (propertyType.isAssignableFrom(LocalDate.class)) {
            return LocalDate.now();
        }
        if (propertyType.isAssignableFrom(LocalDateTime.class)) {
            return LocalDateTime.now();
        }
        if (propertyType.isAssignableFrom(Date.class)) {
            return new Date();
        }
        return null;
    }
}
