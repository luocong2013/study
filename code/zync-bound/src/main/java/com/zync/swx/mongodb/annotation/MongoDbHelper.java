package com.zync.swx.mongodb.annotation;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.mongodb.annotation
 * @description MongoDB 对象模式访问实现类
 * @date 2017-12-5 15:07
 */
public final class MongoDbHelper {

    private MongoDbHelper() {
    }

    /**
     * 插入数据
     *
     * @param obj
     * @param <T>
     */
    public static <T> void insert(T obj) throws MongoDbException {
        if (obj != null) {
            //创建文档集合
            List<Document> documents = new ArrayList<>();
            //集合名
            String tableName = getOrmTableName(obj.getClass());
            try {
                Document document = MongoDbBeanTrans.bean2Document(obj);
                documents.add(document);
            } catch (Exception e) {
                e.printStackTrace();
                throw new MongoDbException("数据写入出错", e);
            }
            if (CollectionUtils.isNotEmpty(documents)) {
                //保存文档
                saveDocuments(documents, tableName);
            }
        }
    }

    /**
     * 批量插入数据
     *
     * @param objs
     * @param <T>
     */
    public static <T> void insertBatch(List<T> objs) throws MongoDbException {
        //创建集合文档
        List<Document> documents = new ArrayList<>();
        //集合名
        String tableName = "";
        for (T obj : objs) {
            if (obj == null) {
                continue;
            }
            tableName = getOrmTableName(obj.getClass());
            try {
                Document document = MongoDbBeanTrans.bean2Document(obj);
                documents.add(document);
            } catch (Exception e) {
                e.printStackTrace();
                throw new MongoDbException("数据写入出错", e);
            }
        }
        if (CollectionUtils.isNotEmpty(documents)) {
            //保存文档
            saveDocuments(documents, tableName);
        }
    }

    /**
     * 查询MongoDB集合的所有数据
     *
     * @param clazz
     * @return
     */
    public static MongoCursor<Document> findAll(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        //集合名
        String tableName = getOrmTableName(clazz);
        //获取集合
        MongoCollection<Document> collection = MongoDbRunner.getMongoCollection(tableName);
        //获取迭代器
        FindIterable<Document> findIterable = collection.find();
        //获取游标
        return findIterable.iterator();
    }

    /**
     * 等于查询
     *
     * @param clazz
     * @param key
     * @param value
     * @return
     */
    public static MongoCursor<Document> findEq(Class<?> clazz, String key, Object value) {
        if (clazz == null) {
            return null;
        }
        //集合名
        String tableName = getOrmTableName(clazz);
        //获取集合
        MongoCollection<Document> collection = MongoDbRunner.getMongoCollection(tableName);
        //获取迭代器
        FindIterable<Document> findIterable = collection.find(Filters.eq(key, value));
        //获取游标
        return findIterable.iterator();
    }

    /**
     * 小于查询
     *
     * @param clazz
     * @param key
     * @param value
     * @return
     */
    public static MongoCursor<Document> findLt(Class<?> clazz, String key, Object value) {
        if (clazz == null) {
            return null;
        }
        //集合名
        String tableName = getOrmTableName(clazz);
        //获取集合
        MongoCollection<Document> collection = MongoDbRunner.getMongoCollection(tableName);
        //获取迭代器
        FindIterable<Document> findIterable = collection.find(Filters.lt(key, value));
        //获取游标
        return findIterable.iterator();
    }

    /**
     * 小于等于查询
     *
     * @param clazz
     * @param key
     * @param value
     * @return
     */
    public static MongoCursor<Document> findLte(Class<?> clazz, String key, Object value) {
        if (clazz == null) {
            return null;
        }
        //集合名
        String tableName = getOrmTableName(clazz);
        //获取集合
        MongoCollection<Document> collection = MongoDbRunner.getMongoCollection(tableName);
        //获取迭代器
        FindIterable<Document> findIterable = collection.find(Filters.lte(key, value));
        //获取游标
        return findIterable.iterator();
    }

    /**
     * 大于查询
     *
     * @param clazz
     * @param key
     * @param value
     * @return
     */
    public static MongoCursor<Document> findGt(Class<?> clazz, String key, Object value) {
        if (clazz == null) {
            return null;
        }
        //集合名
        String tableName = getOrmTableName(clazz);
        //获取集合
        MongoCollection<Document> collection = MongoDbRunner.getMongoCollection(tableName);
        //获取迭代器
        FindIterable<Document> findIterable = collection.find(Filters.gt(key, value));
        //获取游标
        return findIterable.iterator();
    }

    /**
     * 大于等于查询
     *
     * @param clazz
     * @param key
     * @param value
     * @return
     */
    public static MongoCursor<Document> findGte(Class<?> clazz, String key, Object value) {
        if (clazz == null) {
            return null;
        }
        //集合名
        String tableName = getOrmTableName(clazz);
        //获取集合
        MongoCollection<Document> collection = MongoDbRunner.getMongoCollection(tableName);
        //获取迭代器
        FindIterable<Document> findIterable = collection.find(Filters.gte(key, value));
        //获取游标
        return findIterable.iterator();
    }

    /**
     * 不等于查询
     *
     * @param clazz
     * @param key
     * @param value
     * @return
     */
    public static MongoCursor<Document> findNe(Class<?> clazz, String key, Object value) {
        if (clazz == null) {
            return null;
        }
        //集合名
        String tableName = getOrmTableName(clazz);
        //获取集合
        MongoCollection<Document> collection = MongoDbRunner.getMongoCollection(tableName);
        //获取迭代器
        FindIterable<Document> findIterable = collection.find(Filters.ne(key, value));
        //获取游标
        return findIterable.iterator();
    }

    /**
     * in 查询
     *
     * @param clazz
     * @param key
     * @param values
     * @return
     */
    public static MongoCursor<Document> findIn(Class<?> clazz, String key, Object... values) {
        if (clazz == null) {
            return null;
        }
        //集合名
        String tableName = getOrmTableName(clazz);
        //获取集合
        MongoCollection<Document> collection = MongoDbRunner.getMongoCollection(tableName);
        //获取迭代器
        FindIterable<Document> findIterable = collection.find(Filters.in(key, values));
        //获取游标
        return findIterable.iterator();
    }

    /**
     * not in 查询
     *
     * @param clazz
     * @param key
     * @param values
     * @return
     */
    public static MongoCursor<Document> findNin(Class<?> clazz, String key, Object... values) {
        if (clazz == null) {
            return null;
        }
        //集合名
        String tableName = getOrmTableName(clazz);
        //获取集合
        MongoCollection<Document> collection = MongoDbRunner.getMongoCollection(tableName);
        //获取迭代器
        FindIterable<Document> findIterable = collection.find(Filters.nin(key, values));
        //获取游标
        return findIterable.iterator();
    }

    /**
     * 保存文档
     *
     * @param documents
     * @param tableName
     */
    private static void saveDocuments(List<Document> documents, String tableName) throws MongoDbException {
        try {
            //获取集合
            MongoCollection<Document> collection = MongoDbRunner.getMongoCollection(tableName);
            //将文档集合插入数据库集合中
            collection.insertMany(documents);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MongoDbException("存储失败", e);
        }
    }

    /**
     * 获取集合名
     *
     * @param clazz
     * @return
     */
    private static String getOrmTableName(Class<?> clazz) {
        String tableName = clazz.getAnnotation(MongoDbTable.class).tableName();
        return tableName;
    }

}
