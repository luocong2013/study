package com.zync.pools.alioss.client;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AliOssInfo
 *
 * @author luocong
 * @version V1.0.0
 * @description 阿里OSS存储信息
 * @date 2020年03月25日 15:50
 */
@Data
@Builder
@EqualsAndHashCode(exclude = {"securityToken", "userAgent", "proxyHost", "proxyPort", "proxyUsername", "proxyPassword"})
public class AliOssInfo {
    /**
     * 阿里OSS地址
     */
    private String endpoint;
    /**
     * 阿里OSS账号
     */
    private String accessKeyId;
    /**
     * 阿里OSS密码
     */
    private String accessKeySecret;
    /**
     * 阿里OSS桶名
     */
    private String bucketName;

    /**
     * 阿里云oss安全令牌，在使用STS新建一个OSSClient时需要用到此参数
     */
    private String securityToken;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 代理服务器主机地址
     */
    private String proxyHost;
    /**
     * 代理服务器主机端口
     */
    private int proxyPort;
    /**
     * 代理服务器验证的用户名
     */
    private String proxyUsername;
    /**
     * 代理服务器验证的密码
     */
    private String proxyPassword;
}