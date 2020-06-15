package com.zync.swx.mongodb.annotation;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.mongodb.annotation
 * @description MongoDB工具类 对API的简单封装
 * @date 2017-12-5 16:36
 */
public final class MongoDbRunner {

    private static MongoClient mongoClient = null;

    private MongoDbRunner() {
    }

    /**
     * 获取MongoDB连接客户端
     *
     * @return
     */
    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            //连接到MongoDB服务
            ServerAddress address = new ServerAddress("192.168.2.16", 27017);
            List<ServerAddress> addresses = new ArrayList<>();
            addresses.add(address);
            /**
             * 如果Mongo数据库需要验证用户名及密码
             * 使用
             */
            /*MongoCredential credential = MongoCredential.createCredential("userName", "databaseName", "password".toCharArray());
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(credential);*/

            //通过连接认证获取MongoDB连接
            mongoClient = new MongoClient(addresses);
        }
        return mongoClient;
    }

    /**
     * 连接到MongoDB数据库
     *
     * @param databaseName
     * @return
     */
    public static MongoDatabase getMongoDatabase(String databaseName) {
        //连接到MongoDB数据库
        MongoDatabase database = getMongoClient().getDatabase(databaseName);
        return database;
    }

    /**
     * 获取MongoDB集合
     * @param tableName
     * @return
     */
    public static MongoCollection<Document> getMongoCollection(String tableName) {
        return getMongoDatabase("zjtq").getCollection(tableName);
    }

    /**
     * 删除MongoDB数据库
     * @param databaseName
     */
    public static void dropDatabase(String databaseName) {
        //连接到MongoDB数据库
        MongoDatabase database = getMongoDatabase(databaseName);
        //删除数据库
        database.drop();
    }

    /**
     * 删除MongoDB集合
     * @param databaseName
     * @param collectionName
     */
    public static void dropCollection(String databaseName, String collectionName) {
        //连接到MongoDB数据库
        MongoDatabase database = getMongoDatabase(databaseName);
        //删除集合
        database.getCollection(collectionName).drop();
    }

}
