package com.mountain.web.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mountain配置类
 *
 * @author luocong
 * @version 1.0
 * @since 2026/2/10 15:47
 **/
@Getter
@Setter
@SpringBootConfiguration
@ConfigurationProperties(prefix = "mountain")
public class MountainProperties {

    /**
     * 安全认证
     */
    private Auth auth;

    public record Auth(Integer keyExpire, // 加密秘钥过期时间(分钟）
                       String jwtSalt, // JWT签名密钥
                       String[] permitUrls // 无需授权即可访问的url
    ) {
    }
}
