package com.zync.security.springsecurity.passwordencoder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 测试BCrypt加密
 * @date 2020/7/18 17:29
 */
@SpringBootTest
public class TestBCrypt {

    @Test
    public void testBCrypt() {
        // 对密码进行加密
        String hashpw = BCrypt.hashpw("123", BCrypt.gensalt(BCryptPasswordEncoder.BCryptVersion.$2Y.getVersion()));
        System.out.println(hashpw);

        hashpw = BCrypt.hashpw("456", BCrypt.gensalt(BCryptPasswordEncoder.BCryptVersion.$2Y.getVersion()));
        System.out.println(hashpw);


        // 校验密码
        boolean checkpw = BCrypt.checkpw("123", "$2a$10$CGVscpPPjFAnZ5zf/Rk3DOMUdBLBkYLuedxG6af9.Nb7NRb3zw1L6");
        System.out.println(checkpw);

        checkpw = BCrypt.checkpw("123", "$2y$10$Q8j78G6uw0PYqsU6gITlIe0KxtA6Zw77pjti2dHpMglCCK/h6WLZS");
        System.out.println(checkpw);
    }
}
