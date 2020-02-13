package com.zync.pools.minio;

import com.zync.pools.minio.client.MinioInfo;
import io.minio.MinioClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * MinioClientFactory
 *
 * @author luocong
 * @version V1.0.0
 * @description MinioClient工厂类
 * @date 2020年02月13日 14:22
 */
public class MinioClientFactory extends BasePooledObjectFactory<MinioClient> {
    /**
     * minio 连接信息
     */
    private MinioInfo minioInfo;

    public MinioClientFactory(MinioInfo minioInfo) {
        this.minioInfo = minioInfo;
    }

    @Override
    public MinioClient create() throws Exception {
        return new MinioClient(minioInfo.getEndpoint(), minioInfo.getAccessKey(), minioInfo.getSecretKey());
    }

    @Override
    public PooledObject<MinioClient> wrap(MinioClient minioClient) {
        return new DefaultPooledObject<>(minioClient);
    }

    @Override
    public boolean validateObject(PooledObject<MinioClient> p) {
        System.out.println("validate...");
        return super.validateObject(p);
    }

    @Override
    public void destroyObject(PooledObject<MinioClient> p) throws Exception {
        super.destroyObject(p);
    }
}
