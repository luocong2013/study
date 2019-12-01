package com.zync.func;

import com.zync.common.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 方法引用可以直接使用构造函数
 * @date 2019/11/26 22:25
 */
public class ConstrMethodRef {

    @FunctionalInterface
    interface UserFactory<U extends User> {
        U create(String username, String password, int age);
    }

    static UserFactory<User> uf = User::new;

    public static void main(String[] args){
        List<User> users = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            users.add(uf.create(i + "", i + "", i));
        }
        users.stream().map(User::getUsername).forEach(System.out::println);
    }
}
