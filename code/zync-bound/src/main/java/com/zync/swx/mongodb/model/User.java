package com.zync.swx.mongodb.model;


import com.zync.swx.mongodb.annotation.MongoDbColumn;
import com.zync.swx.mongodb.annotation.MongoDbTable;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.mongodb.model
 * @description TODO
 * @date 2017-12-5 15:01
 */
@MongoDbTable(tableName = "user")
public class User {
    @MongoDbColumn(columnName = "username")
    private String username;
    @MongoDbColumn(columnName = "password")
    private String password;
    @MongoDbColumn(columnName = "idcard")
    private String idcard;
    @MongoDbColumn(columnName = "age")
    private Integer age;

    public User() {
    }

    public User(String username, String password, String idcard, Integer age) {
        this.username = username;
        this.password = password;
        this.idcard = idcard;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
