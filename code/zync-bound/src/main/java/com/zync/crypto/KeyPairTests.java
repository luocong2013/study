package com.zync.crypto;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.util.Arrays;

/**
 * @author 01396453(luocong)
 * @version 3.0.6
 * @description KeyPair测试
 * @date 2020/11/25 12:05
 */
public class KeyPairTests {

    public static void main(String[] args) {
        KeyPair keyPair = SecureUtil.generateKeyPair(AsymmetricAlgorithm.RSA.getValue(), 2048);
        System.out.println("公钥----：" + Base64.encode(keyPair.getPublic().getEncoded()));
        System.out.println("私钥----：" + Base64.encode(keyPair.getPrivate().getEncoded()));

        SecretKey desSecretKey = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue());
        System.out.println("DES SecretKey: " + Base64.encode(desSecretKey.getEncoded()));

        System.out.println(Arrays.toString(desSecretKey.getEncoded()));

        char[] chars = HexUtil.encodeHex(desSecretKey.getEncoded());
        System.out.println(Arrays.toString(chars));

        SecretKey aesSecretKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), 256);
        System.out.println("AES SecretKey: " + Base64.encode(aesSecretKey.getEncoded()));
    }
}
