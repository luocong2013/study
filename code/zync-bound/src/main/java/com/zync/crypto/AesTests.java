package com.zync.crypto;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description AES对称加密算法
 * @date 2020/11/25 14:53
 */
public class AesTests {

    private static final String SECRET_KEY_BASE64 = "TxS9hwafuhc2KZca3h4cV8jafv++/zHD/1Av4YD8ef0=";

    public static void main(String[] args) {
        AES aes = SecureUtil.aes(SECRET_KEY_BASE64.getBytes());
        String base64 = aes.encryptBase64("张三");
        System.out.println(base64);

        String decryptStr = aes.decryptStr(base64);
        System.out.println(decryptStr);
    }
}
