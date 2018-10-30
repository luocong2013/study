package com.ccyw.lucene.annotation;

import com.ccyw.lucene.bean.Article;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.Term;

import java.lang.reflect.Field;

/**
 * @author luoc
 * @version V1.0.0
 * @description Lucene bean转document
 * @date 2018/10/27 22:48
 */
public class LuceneBeanTrans {

    private LuceneBeanTrans() {
    }

    /**
     * JavaBean转换为Document
     * @param obj
     * @param <T>
     * @return
     * @throws LuceneException
     */
    public static <T> Document bean2Document(T obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (ArrayUtils.isEmpty(fields)) {
            return null;
        }
        //创建文档
        Document document = new Document();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(LuceneField.class)) {
                continue;
            }
            // 取消Java语言访问检查，否则不能访问private字段
            field.setAccessible(true);
            String fieldName = field.getAnnotation(LuceneField.class).fieldName();
            if (StringUtils.isBlank(fieldName)) {
                continue;
            }
            Object fieldObj = field.get(obj);
            if (fieldObj.getClass().isArray()) {
                System.out.println("类型不支持");
                continue;
            }
            document.add(new TextField(fieldName, fieldObj.toString(), Store.YES));
        }
        return document;
    }

    /**
     * JavaBean转换为Term
     * @param obj
     * @param name
     * @param <T>
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static <T> Term bean2Term(T obj, String name) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(name);
        if (field == null) {
            return null;
        }
        if (!field.isAnnotationPresent(LuceneField.class)) {
            return null;
        }
        // 取消Java语言访问检查，否则不能访问private字段
        field.setAccessible(true);
        String fieldName = field.getAnnotation(LuceneField.class).fieldName();
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        Object fieldObj = field.get(obj);
        if (fieldObj.getClass().isArray()) {
            System.out.println("类型不支持");
            return null;
        }
        Term term = new Term(fieldName, fieldObj.toString());
        return term;
    }

    /**
     * 文档转对象
     * @param document
     * @return
     */
    public static Article document2Article(Document document) {
        Article article = new Article();
        article.setId(Integer.parseInt(document.get("id")));
        article.setTitle(document.get("title"));
        article.setContent(document.get("content"));
        return article;
    }
}
