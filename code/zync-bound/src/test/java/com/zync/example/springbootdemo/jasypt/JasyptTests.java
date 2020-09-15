package com.zync.example.springbootdemo.jasypt;

import com.zync.example.springbootdemo.SpringbootDemoApplication;
import com.zync.example.springbootdemo.config.JasyptUserConfig;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 01396453(luocong)
 * @version 3.0.3
 * @description jasypt测试
 * @date 2020/9/15 11:03
 */
@SpringBootTest(classes = SpringbootDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JasyptTests {

    @Autowired
    private StringEncryptor encryptor;

    @Autowired
    private JasyptUserConfig jasyptUserConfig;

    @Test
    public void encrypt() {
        String name = encryptor.encrypt("罗聪");
        System.out.println(name);
        String password = encryptor.encrypt("1qaz2wsx..");
        System.out.println(password);
        String telphone = encryptor.encrypt("18380804040");
        System.out.println(telphone);
    }

    @Test
    public void decrypt() {
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword("jasypt");
        System.out.println(encryptor.decrypt("GeD9/L279L1kyZ6UNCNuRP747OqizHpj8DFZ0Zrz/RFar0Ot1lqLnJjGZOJr5rHR"));
    }

    @Test
    public void userInfo() {
        System.out.println("用户名：" + jasyptUserConfig.getUsername());
        System.out.println("密  码：" + jasyptUserConfig.getPassword());
        System.out.println("电  话：" + jasyptUserConfig.getTelphone());
    }
}
