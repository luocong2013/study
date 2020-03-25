package com.zync.pools.alioss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.zync.pools.alioss.client.AliOssInfo;
import com.zync.pools.alioss.pool.AliOssClientPoolConfig;

import java.util.List;

/**
 * AliOssClientTest
 *
 * @author luocong
 * @version V1.0.0
 * @description 阿里OSS测试类
 * @date 2020年03月25日 17:00
 */
public class AliOssClientTest {

    public static void main(String[] args) throws Exception {
        AliOssClientFactory factory = new AliOssClientFactory();
        AliOssClientPoolConfig config = new AliOssClientPoolConfig();
        config.setMaxTotal(10);

        AliOssClientPoolManager manager = new AliOssClientPoolManager(factory, config);

        AliOssInfo ossInfo = AliOssInfo.builder()
                .endpoint("http://oss-cn-hangzhou.aliyuncs.com")
                .accessKeyId("<yourAccessKeyId>")
                .accessKeySecret("<yourAccessKeySecret>")
                .bucketName("<yourBucketName>")
                .build();
        OSS ossClient = manager.getOssClient(ossInfo);
        ObjectListing objectListing = ossClient.listObjects(ossInfo.getBucketName());
        List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : objectSummaries) {
            System.out.println(s.getKey());
        }
        System.out.println(ossClient);
        manager.releaseOssClient(ossInfo, ossClient);

    }
}
