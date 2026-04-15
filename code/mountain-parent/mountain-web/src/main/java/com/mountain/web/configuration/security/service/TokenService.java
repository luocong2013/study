package com.mountain.web.configuration.security.service;

import com.mountain.common.utils.JwtUtil;
import com.mountain.web.configuration.MountainProperties;
import com.mountain.web.configuration.security.details.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 生成Token 服务类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/4/15 16:21
 **/
@Service
@RequiredArgsConstructor
public class TokenService {

    private final MountainProperties mountainProperties;

    /**
     * 创建Token
     *
     * @param user 用户
     * @return token
     */
    public String createToken(SecurityUser user) {
        String username = user.getUsername();
        String audience = "WEB";
        MountainProperties.Auth auth = mountainProperties.getAuth();
        int minutes = auth.keyExpire();
        String salt = auth.jwtSalt();
        return JwtUtil.create(username, audience, minutes, salt);
    }

}
