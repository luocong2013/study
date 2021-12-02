package com.zync.crypto;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 访问密钥 生成和校验工具类
 *
 * @author luocong
 * @version v1.0
 * @date 2021/12/2 15:09
 */
@UtilityClass
public class AccessKeyUtil {

    private final PasswordEncoder ENCODER = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);

    /**
     * 生成访问密钥
     * @param objects 参数
     * @return
     */
    public String accessKey(Object... objects) {
        return ENCODER.encode(encrypt(objects));
    }

    /**
     * 校验访问密钥是否有效
     * @param accessKey 访问密钥
     * @param objects 参数
     * @return
     */
    public boolean isValid(String accessKey, Object... objects) {
        return ENCODER.matches(encrypt(objects), accessKey);
    }

    /**
     * 加密
     * @param objects 参数
     * @return
     */
    private String encrypt(Object... objects) {
        return DigestUtils.sha512Hex(key(objects));
    }

    /**
     * 生成加密的key
     * @param objects 参数
     * @return
     */
    private String key(Object... objects) {
        return StringUtils.joinWith("_", objects);
    }

    public static void main(String[] args) {
        String accessKey = accessKey(7645146981072896L, 11756326680526848L);

        System.out.println(accessKey);

        boolean valid = isValid(null, 7645146981072896L, 11756326680526848L);
        System.out.println(valid);
    }
}
