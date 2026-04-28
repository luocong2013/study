package com.mountain.web.security.service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.mountain.common.base.enums.RedisKeyEnum;
import com.mountain.common.base.vo.PublicKeyVO;
import com.mountain.common.exception.BizException;
import com.mountain.common.holder.RedisCacheHolder;
import com.mountain.common.holder.RedissonHolder;
import com.mountain.common.utils.Assert;
import com.mountain.common.utils.RSAUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.Map;

import static com.mountain.common.base.enums.RedisKeyEnum.*;

/**
 * 加解密服务
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/28 16:43
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class EncryptDecryptService {

    private final RedissonHolder redissonHolder;
    private final RedisCacheHolder redisCacheHolder;
    private final PasswordEncoder passwordEncoder;

    public boolean checkPassword(String ticket, String password, String encodedPassword) {
        String rawPassword = decryptPassword(ticket, password);
        try {
            return passwordEncoder.matches(rawPassword, encodedPassword);
        } catch (Exception e) {
            log.error("check password error", e);
            return false;
        }
    }

    /**
     * 解密密码
     *
     * @param ticket   登录票据
     * @param password 密码
     * @return 解密后的密码
     */
    public String decryptPassword(String ticket, String password) {
        String cacheKey = AUTH_LOGIN_RSA.getKey(ticket);
        String priKey = redisCacheHolder.get(cacheKey);
        Assert.notBlank(priKey, () -> new BizException("the rsa key is expired."));
        redisCacheHolder.del(cacheKey);
        try {
            return RSAUtil.decrypt(priKey, password);
        } catch (Exception e) {
            log.error("the password decrypt error", e);
            throw new BizException("the password decrypt error", e);
        }
    }

    /**
     * 获取公钥和私钥
     *
     * @return PublicKeyVO
     */
    public PublicKeyVO getPublicKey() {
        String cacheKey = AUTH_LOGIN_RSA_KEY.getKey();
        if (redisCacheHolder.hasKey(cacheKey)) {
            String publicKey = redisCacheHolder.hGet(cacheKey, "publicKey");
            String privateKey = redisCacheHolder.hGet(cacheKey, "privateKey");
            return getPublicKey0(publicKey, privateKey);
        }
        String lockKey = AUTH_LOGIN_RSA_LOCK.getKey();
        if (!redissonHolder.tryLockNoTime(lockKey)) {
            throw new BizException("系统繁忙，请稍后再试.");
        }
        try {
            return newPublicKey();
        } finally {
            redissonHolder.unlock(lockKey);
        }
    }

    /**
     * 新建公钥和私钥
     *
     * @return PublicKeyVO
     */
    private PublicKeyVO newPublicKey() {
        KeyPair keyPair;
        try {
            keyPair = RSAUtil.getKeyPair();
        } catch (Exception e) {
            log.error("New publicKey error.", e);
            throw new BizException("New publicKey error", e);
        }
        String publicKey = RSAUtil.getPublicKey(keyPair);
        String privateKey = RSAUtil.getPrivateKey(keyPair);
        Map<String, Object> map = Map.of("publicKey", publicKey, "privateKey", privateKey);
        RedisKeyEnum key = AUTH_LOGIN_RSA_KEY;
        String cacheKey = key.getKey();
        redisCacheHolder.hmSet(cacheKey, map);
        redisCacheHolder.expire(cacheKey, key.getExpire(), key.getTimeUnit());
        return getPublicKey0(publicKey, privateKey);
    }

    /**
     * 获取公钥和私钥
     *
     * @param publicKey  公钥
     * @param privateKey 私钥
     * @return PublicKeyVO
     */
    private PublicKeyVO getPublicKey0(String publicKey, String privateKey) {
        String ticket = IdWorker.getIdStr();
        RedisKeyEnum key = AUTH_LOGIN_RSA;
        redisCacheHolder.set(key.getKey(ticket), privateKey, key.getExpire(), key.getTimeUnit());
        return new PublicKeyVO(publicKey, ticket);
    }
}
