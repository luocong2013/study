package com.zync.reflect.domain;

/**
 * 用户信息
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/13 09:55
 */
public class User {
    /**
     * 用户ID
     */
    private long id;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 年龄
     */
    private int age;
    /**
     * 邮箱
     */
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
