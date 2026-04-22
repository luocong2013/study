package com.mountain.common.utils;

import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import lombok.experimental.UtilityClass;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * RSA算法工具类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/22 14:01
 **/
@UtilityClass
public class RSAUtil {

    //========================================生成公钥、私钥START===========================================//

    /**
     * 生成密钥对
     */
    public KeyPair getKeyPair() {
        return SecureUtil.generateKeyPair(AsymmetricAlgorithm.RSA.getValue(), 2048);
    }

    /**
     * 获取公钥（Base64编码）
     *
     * @param keyPair 密钥对
     * @return 公钥（Base64编码）
     */
    public String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] encoded = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(encoded);
    }

    /**
     * 获取私钥（Base64编码）
     *
     * @param keyPair 密钥对
     * @return 私钥（Base64编码）
     */
    public String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] encoded = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(encoded);
    }

    //========================================生成公钥、私钥END===========================================//


    //========================================加密、解密START===========================================//

    /**
     * RSA公钥加密数据
     *
     * @param pubKey    Base64 编码的公钥
     * @param plainText 明文
     * @return Base64 编码的加密数据
     */
    public String encrypt(String pubKey, String plainText) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(pubKey);
        PublicKey publicKey = KeyUtil.generateRSAPublicKey(decoded);
        Cipher cipher = Cipher.getInstance(AsymmetricAlgorithm.RSA.getValue());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * RSA私钥加密数据
     *
     * @param priKey     Base64 编码的私钥
     * @param plainText  明文
     * @return           Base64 编码的加密数据
     */
    public String encryptPrivate(String priKey, String plainText) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(priKey);
        PrivateKey privateKey = KeyUtil.generateRSAPrivateKey(decoded);
        Cipher cipher = Cipher.getInstance(AsymmetricAlgorithm.RSA.getValue());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * RAS私钥解密数据
     *
     * @param priKey     Base64 编码的私钥
     * @param cipherText Base64 编码的加密数据
     * @return 明文
     */
    public String decrypt(String priKey, String cipherText) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(priKey);
        PrivateKey privateKey = KeyUtil.generateRSAPrivateKey(decoded);
        Cipher cipher = Cipher.getInstance(AsymmetricAlgorithm.RSA.getValue());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // Base64 编码的加密数据
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    /**
     * RSA公钥解密数据
     *
     * @param pubKey      Base64 编码的公钥
     * @param cipherText  Base64 编码的加密数据
     * @return            明文
     */
    public String decryptPublic(String pubKey, String cipherText) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(pubKey);
        PublicKey publicKey = KeyUtil.generateRSAPublicKey(decoded);
        Cipher cipher = Cipher.getInstance(AsymmetricAlgorithm.RSA.getValue());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        // Base64 编码的加密数据
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    //========================================加密、解密END===========================================//
}
