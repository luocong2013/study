package com.zync.ibed.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption Minio服务
 * @date 2020/1/4 16:06
 */
@Service
public class TiebaMinioService extends AbstractMinioService implements InitializingBean {
    /**
     * Minio服务地址
     */
    @Value("${minio.tieba.endpoint}")
    private String endpoint;

    /**
     * 用户名
     */
    @Value("${minio.tieba.access-key}")
    private String accessKey;

    /**
     * 密码
     */
    @Value("${minio.tieba.secret-key}")
    private String secretKey;

    /**
     * 桶名
     */
    @Value("${minio.tieba.bucket:tieba}")
    private String bucket;

    @Override
    protected String getBucket() {
        return this.bucket;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.minioClient = new MinioClient(endpoint, accessKey, secretKey);
        this.checkAndInitBucket();
    }
}
