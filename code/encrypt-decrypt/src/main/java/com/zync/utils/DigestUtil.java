package com.zync.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2022/6/22 14:25
 */
public final class DigestUtil {

    private DigestUtil () {}

    public static String md5Hex(String data) {
        return encodeHexString(md5(data));
    }

    public static String encodeHexString(final byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            int bt = b & 0xff;
            if (bt < 16) {
                builder.append(0);
            }
            builder.append(Integer.toHexString(bt));
        }
        return builder.toString();
    }

    public static byte[] md5(String data) {
        byte[] bytes = data == null ? null : data.getBytes(StandardCharsets.UTF_8);
        return getMd5Digest().digest(bytes);
    }


    public static MessageDigest getMd5Digest() {
        return getDigest("MD5");
    }

    public static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException var2) {
            throw new IllegalArgumentException(var2);
        }
    }
}
