package com.zync.crypto;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author luocong
 * @version 1.0
 * @description
 * @since 2025/10/30 10:08
 **/
public class ShaTests {

    public static void main(String[] args) {
        System.out.println(DigestUtils.sha512Hex("123456"));
        System.out.println(DigestUtils.sha512Hex("123456"));
        System.out.println(DigestUtils.sha512Hex("123456"));

        System.out.println(DigestUtils.md5Hex("123456"));
        System.out.println(DigestUtils.md5Hex("123456"));
        System.out.println(DigestUtils.md5Hex("123456"));

        PasswordEncoder ENCODER = new BCryptPasswordEncoder();
        String encode = ENCODER.encode("123456");
        System.out.println(encode);
        System.out.println(ENCODER.matches("123456", encode));
    }
}
