package com.zync.crypto;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description DES对称加密算法
 * @date 2020/11/25 15:19
 */
public class DesTests {

    private static final String SECRET_KEY_BASE64 = "m4k97CXg+Hw=";

    public static void main(String[] args) {
        DES des = SecureUtil.des(SECRET_KEY_BASE64.getBytes());
        String base64 = des.encryptBase64("李四");
        System.out.println(base64);

        String decryptStr = des.decryptStr(base64);
        System.out.println(decryptStr);
    }
}
