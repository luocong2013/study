package com.zync.pools.minio.client;

import lombok.Builder;
import lombok.Data;

/**
 * MinioInfo
 *
 * @author luocong
 * @version V1.0.0
 * @description minio连接信息
 * @date 2020年02月13日 14:10
 */
@Data
@Builder
public class MinioInfo {
    /**
     * minio连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
}
