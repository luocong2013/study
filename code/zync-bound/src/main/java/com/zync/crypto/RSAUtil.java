package com.zync.crypto;

import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * rsa工具类
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/7/25 11:49
 */
@UtilityClass
public class RSAUtil {

    //========================================生成公钥、私钥START===========================================//

    /**
     * 生成密钥对
     * @return
     */
    public KeyPair generateKeyPair() {
        return SecureUtil.generateKeyPair(AsymmetricAlgorithm.RSA.getValue(), 2048);
    }

    /**
     * 获取公钥（Base64编码）
     * @param keyPair
     * @return
     */
    public String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] encoded = publicKey.getEncoded();
        return Base64Utils.encodeToString(encoded);
    }

    /**
     * 获取私钥（Base64编码）
     * @param keyPair
     * @return
     */
    public String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] encoded = privateKey.getEncoded();
        return Base64Utils.encodeToString(encoded);
    }

    //========================================生成公钥、私钥END===========================================//


    //========================================加密、解密START===========================================//

    /**
     * RSA公钥加密数据
     *
     * @param pubKey      Base64 编码的公钥
     * @param plainText   明文
     * @return            Base64 编码的加密数据
     * @throws Exception
     */
    public String encrypt(String pubKey, String plainText) throws Exception {
        byte[] decoded = Base64Utils.decodeFromString(pubKey);
        PublicKey publicKey = KeyUtil.generateRSAPublicKey(decoded);
        Cipher cipher = Cipher.getInstance(AsymmetricAlgorithm.RSA.getValue());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeToString(bytes);
    }

    /**
     * RAS私钥解密数据
     *
     * @param priKey       Base64 编码的私钥
     * @param cipherText   Base64 编码的加密数据
     * @return             明文
     * @throws Exception
     */
    public String decrypt(String priKey, String cipherText) throws Exception {
        byte[] decoded = Base64Utils.decodeFromString(priKey);
        PrivateKey privateKey = KeyUtil.generateRSAPrivateKey(decoded);
        Cipher cipher = Cipher.getInstance(AsymmetricAlgorithm.RSA.getValue());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // Base64 编码的加密数据
        byte[] bytes = Base64Utils.decodeFromString(cipherText);
        return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    //========================================加密、解密END===========================================//

    //========================================TEST===========================================//

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateKeyPair();
        String pubKey = getPublicKey(keyPair);
        String priKey = getPrivateKey(keyPair);

        System.out.println("公钥: " + pubKey);
        System.out.println("私钥: " + priKey);

        String plainText = DigestUtils.sha512Hex("plainText");

        String cipherText = encrypt(pubKey, plainText);

        System.out.println("加密数据: " + cipherText);

        System.out.println(decrypt(priKey, cipherText));
    }

}
