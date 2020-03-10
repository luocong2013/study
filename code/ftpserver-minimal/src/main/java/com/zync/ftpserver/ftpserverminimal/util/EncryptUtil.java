package com.zync.ftpserver.ftpserverminimal.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.Md5PasswordEncryptor;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.SaltedPasswordEncryptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 加密工具类
 * @date 2020/3/10 20:51
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EncryptUtil {
    private final static String MD5 = "md5";
    private final static String SALTED = "salted";
    private final static String CLEAR_TEXT = "clearText";
    private static Map<String, PasswordEncryptor> encryptorMap = new HashMap<>();

    static {
        encryptorMap.put(MD5, new Md5PasswordEncryptor());
        encryptorMap.put(SALTED, new SaltedPasswordEncryptor());
        encryptorMap.put(CLEAR_TEXT, new ClearTextPasswordEncryptor());
    }

    /**
     * 获取加密器
     * @param type
     * @return
     */
    public static PasswordEncryptor getEncrypt(String type) {
        if (StringUtils.isBlank(type)) {
            throw new IllegalArgumentException("The encrypt type parameter must not be empty!");
        }
        return encryptorMap.get(type);
    }
}
