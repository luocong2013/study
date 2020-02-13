package com.zync.pools.minio;

import com.zync.pools.minio.client.MinioInfo;
import com.zync.pools.minio.pool.MinioClientPoolConfig;
import io.minio.MinioClient;

/**
 * MinioClientTest
 *
 * @author luocong
 * @version V1.0.0
 * @description MinioClient 测试
 * @date 2020年02月13日 15:22
 */
public class MinioClientTest {

    public static void main(String[] args) throws Exception {
        MinioInfo minioInfo = MinioInfo.builder().endpoint("http://172.25.17.54:9000/").accessKey("dev").secretKey("123456789").build();
        MinioClientFactory factory = new MinioClientFactory(minioInfo);
        MinioClientPoolConfig config = new MinioClientPoolConfig();
        config.setMaxTotal(2);

        MinioClientPoolManager manager = new MinioClientPoolManager(factory, config);
        MinioClient minioClient = manager.getMinioClient();
        System.out.println(minioClient);
        manager.releaseMinioClient(minioClient);

        MinioClient minioClient2 = manager.getMinioClient();
        System.out.println(minioClient2);
        manager.releaseMinioClient(minioClient2);

        MinioClient minioClient3 = manager.getMinioClient();
        System.out.println(minioClient3);
        manager.releaseMinioClient(minioClient3);
    }
}
