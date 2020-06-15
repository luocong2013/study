package com.zync.swx.mongodb.annotation;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.lang.reflect.Field;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.mongodb.annotation
 * @description MongoDB bean转document
 * @date 2017-12-5 16:00
 */
public class MongoDbBeanTrans {

    private MongoDbBeanTrans() {
    }

    /**
     * JavaBean转换为Document
     *
     * @param obj
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Document bean2Document(T obj) throws Exception {
        //创建文档
        Document document = new Document();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(MongoDbColumn.class)) {
                continue;
            }
            //取消Java语言访问检查，否则不能访问private字段
            field.setAccessible(true);
            String columnName = field.getAnnotation(MongoDbColumn.class).columnName();
            if (StringUtils.isBlank(columnName)) {
                continue;
            }
            Object fieldObj = field.get(obj);
            if (fieldObj.getClass().isArray()) {
                System.out.println("类型不支持");
                continue;
            }
            document.append(columnName, fieldObj);
        }
        return document;
    }

}
