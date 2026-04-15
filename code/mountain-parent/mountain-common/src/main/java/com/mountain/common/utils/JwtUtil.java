package com.mountain.common.utils;

import com.mountain.common.base.Const;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * jwt token工具类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/15 14:51
 **/
@Slf4j
public class JwtUtil {

    //===============================================创建jwt token=======================================================//

    /**
     * 创建 jwt token
     *
     * @param subject   主题
     * @param audience  受众
     * @param minutes   时间（分钟）
     * @param salt      jwt密钥
     * @return jwt token
     */
    public static String create(String subject, String audience, int minutes, String salt) {
        return create(subject, audience, minutes, null, salt);
    }

    /**
     * 创建 jwt token
     *
     * @param subject   主题
     * @param audience  受众
     * @param minutes   时间（分钟）
     * @param claims    claims
     * @param salt      jwt密钥
     * @return jwt token
     */
    public static String create(String subject, String audience, int minutes, Map<String, Object> claims, String salt) {
        Date now = new Date();
        return Jwts.builder()
                // 签发人
                .issuer(Const.SELF_SERVICE_NAME)
                // 主题 - 存用户名
                .subject(subject)
                .audience().add(audience).and()
                .expiration(DateUtils.addMinutes(now, minutes))
                .issuedAt(now)
                .claims(claims)
                .signWith(key(salt))
                //.signWith(key(salt), SignatureAlgorithm.HS512)
                .compact();
    }


    //===============================================解析jwt token=======================================================//

    /**
     * 获取用户名
     *
     * @param jwtToken  jwt token
     * @param audience  受众
     * @param salt      jwt密钥
     * @return 用户名
     */
    public static String getUsername(String jwtToken, String audience, String salt) {
        String subject = parse(jwtToken, audience, salt).getSubject();
        log.info("current username: {}", subject);
        return subject;
    }

    /**
     * 解析 jwt token
     *
     * @param jwtToken  jwt token
     * @param audience  受众
     * @param salt      jwt密钥
     * @return Claims
     */
    public static Claims parse(String jwtToken, String audience, String salt) {
        return Jwts.parser()
                .requireIssuer(Const.SELF_SERVICE_NAME)
                .requireAudience(audience)
                .verifyWith(key(salt))
                .build()
                .parseSignedClaims(Strings.CS.replace(jwtToken, "Bearer ", StringUtils.EMPTY))
                .getPayload();
    }

    /**
     * 获取 key
     *
     * @param salt jwt密钥
     * @return Key
     */
    private static SecretKey key(String salt) {
        return Keys.hmacShaKeyFor(DigestUtils.sha512(salt));
    }
}
