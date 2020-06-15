package com.zync.swx.mongodb.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.zync.swx.mongodb.annotation.MongoDbRunner;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.mongodb.dao
 * @description TODO
 * @date 2017-12-5 13:53
 */
public class MongoDBDao {
    private MongoDatabase database = MongoDbRunner.getMongoDatabase("zjtq");

    /**
     * 创建集合
     * @param collectionName
     */
    public void createCollection(String collectionName) {
        database.createCollection(collectionName);
        System.out.println("集合创建成功");
    }

    /**
     * 插入文档
     * @param collectionName
     */
    public void insert(String collectionName) {
        //获取集合
        MongoCollection<Document> collection = database.getCollection(collectionName);
        //1. 创建文档Document
        Document document = new Document()
                .append("title", "MongoDB Java Demo")
                .append("dec", "MongoDB database")
                .append("likes", 150)
                .append("by", "LuoC");
        //2. 创建文档集合
        List<Document> documents = new ArrayList<>();
        documents.add(document);
        //3. 将文档集合插入数据库集合中
        collection.insertMany(documents);
        System.out.println("文档插入成功");
    }

    /**
     * 检索所有文档
     * @param collectionName
     */
    public void find(String collectionName) {
        //获取集合
        MongoCollection<Document> collection = database.getCollection(collectionName);
        //1. 获取迭代器
        //获取集合中的所有文档
//		FindIterable<Document> findIterable = collection.find();
        //获取过滤后的文档
//		FindIterable<Document> findIterable = collection.find(Filters.eq("likes", 90));
        //排序文档
//		FindIterable<Document> findIterable = collection.find(Filters.exists("likes")).sort(Filters.eq("likes", 1));

        FindIterable<Document> findIterable = collection.find();
        //2. 获取游标
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next().toJson());
        }
    }

    /**
     * 更新文档
     * @param collectionName
     */
    public void update(String collectionName) {
        //获取集合
        MongoCollection<Document> collection = database.getCollection(collectionName);
        //更新文档，将文档中likes=100的文档修改为likes=200
        collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes", 200)));
        System.out.println("文档更新成功");
    }

    /**
     * 删除文档
     * @param collectionName
     */
    public void delete(String collectionName) {
        //获取集合
        MongoCollection<Document> collection = database.getCollection(collectionName);
        //删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("likes", 200));
        //删除所有符合条件的文档
        collection.deleteMany(Filters.gte("likes", 300));
        System.out.println("文档删除完毕");
    }
}
