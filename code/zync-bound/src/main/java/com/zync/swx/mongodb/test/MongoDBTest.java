package com.zync.swx.mongodb.test;

import com.mongodb.client.MongoCursor;
import com.zync.swx.mongodb.annotation.MongoDbHelper;
import com.zync.swx.mongodb.model.User;
import org.bson.Document;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.mongodb.test
 * @description TODO
 * @date 2017-12-5 13:55
 */
public class MongoDBTest {

    public static void main(String[] args) {
        /*MongoDbRunner.dropCollection("zjtq", "user");

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            User user = new User("张三" + i, "pwd123" + i, "51111119990912091" + i, i);
            users.add(user);
        }
        MongoDbHelper.insertBatch(users);*/


//        MongoCursor<Document> mongoCursor = MongoDbHelper.findAll(User.class);
//        MongoCursor<Document> mongoCursor = MongoDbHelper.findEq(User.class, "username", "张三9991");
//        MongoCursor<Document> mongoCursor = MongoDbHelper.findLt(User.class, "age", 111);
//        MongoCursor<Document> mongoCursor = MongoDbHelper.findLte(User.class, "age", 111);
        MongoCursor<Document> mongoCursor = MongoDbHelper.findIn(User.class, "age", 111, 999, 222);
//        MongoCursor<Document> mongoCursor = MongoDbHelper.findNin(User.class, "age", 111, 9999, 222);

        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next().toJson());
        }
    }

}
